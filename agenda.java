import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class agenda {
    private JTextField txtnumero;
    private JTextField txtnombre;
    private JTextField txtapellido;
    private JTextField txtdireccion;
    private JComboBox cboperadora;
    private JButton btnbuscar;
    private JButton btnmodificar;
    private JButton btcrear;
    private JButton btneliminar;
    private JPanel panel1;


    ArrayList oListaOperadoras;
    private ArrayList <Operadora> operadorap;

    public void rellenarOperadoras()
    {
        connection=getConection();
        cboperadora.removeAllItems();
        oListaOperadoras=getListOperadorap();
        Iterator iterator = oListaOperadoras.iterator();
        while (iterator.hasNext()) {
            Operadora oOperadora = (Operadora) iterator.next();
            cboperadora.addItem(oOperadora);
        }
    }
    public ArrayList getListOperadorap()
    {

        ArrayList oOperadoraO=new ArrayList();
        Operadora oOPER=null;
        Statement consulta;
        ResultSet resultado;
        connection=getConection();
        try {
            consulta= connection.createStatement();
            resultado=consulta.executeQuery("select * from operadora");
            while (resultado.next())
            {
                oOPER=new Operadora();

                oOPER.setOperadora(resultado.getString("operadora"));
                oOperadoraO.add(oOPER);
            }

        }catch (SQLException e)
        {

        }
        return oOperadoraO;}

    PreparedStatement ps;
    ResultSet rs;
    Statement st;

    private static boolean isNumeric(String cadena){
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe){
            return false;
        }
    }
Connection connection;
    public agenda() {
        oListaOperadoras=new ArrayList();
        rellenarOperadoras();


        btcrear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                connection = getConection();
                    String opero = cboperadora.getSelectedItem().toString();
                    if (txtnumero.getText().equals("")) {

                        JOptionPane.showMessageDialog(null, "Uno varios campos vacios");
                    } else {
                        if (isNumeric(txtnumero.getText())) {
                        try {

                            st = connection.createStatement();
                            rs = st.executeQuery("select * from usuario where numero=" + txtnumero.getText() + ";");

                            if (rs.next()) {

                                JOptionPane.showMessageDialog(null, "Codigo ya ocupado||Producto ya registrado");
                            } else {
                                try {

                                    ps = connection.prepareStatement("INSERT INTO usuario(numero,nombre,apellido,direccion,operadora) VALUES (?,?,?,?,?)");


                                    ps.setString(1, txtnumero.getText());
                                    ps.setString(2, txtnombre.getText());
                                    ps.setString(3, txtapellido.getText());
                                    ps.setString(4, txtdireccion.getText());
                                    ps.setString(5, opero);


                                    int res = ps.executeUpdate();

                                    if (res > 0) {
                                        JOptionPane.showMessageDialog(null, "Se creo de manera correta");

                                    }

                                } catch (HeadlessException | SQLException f) {
                                    System.out.println(f);

                                }
                            }
                        } catch (Exception s) {

                        }
                    } else {
                            JOptionPane.showMessageDialog(null,"El campo debe ser un numero");
                        }
                }
            }


        });
        cboperadora.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnbuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection con;

                String oper = cboperadora.getSelectedItem().toString();
                if (isNumeric(txtnumero.getText())) {

                try {
                    con = getConection();
                    st = con.createStatement();
                    ResultSet rs;
                    ResultSet rsc;
                    rs = st.executeQuery("select * from usuario where numero=" + txtnumero.getText() + ";");
                    if(rs.next()){
                        do{
                            txtnombre.setText(rs.getString("nombre"));
                            txtapellido.setText(rs.getString("apellido"));
                            txtdireccion.setText(rs.getString("direccion"));
                            cboperadora.setSelectedItem(rs.getString("operadora"));
                        }while (rs.next());
                        JOptionPane.showMessageDialog(null,"Numero Encontrado||Listo");
                    }else {
                        JOptionPane.showMessageDialog(null,"El numero aun no ha sido registrado || No se encuentra en la base de datos");
                    }
                } catch (Exception s) {

                }

            }
            else
            {
              JOptionPane.showMessageDialog(null,"El campo debe tener un numero");
            }}
        });
        btnmodificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String oper = cboperadora.getSelectedItem().toString();

                    connection = getConection();
                    st = connection.prepareStatement("UPDATE usuario SET nombre = ?, apellido = ? ,direccion  = ? ,operadora = ? = ? WHERE numero ="+txtnumero.getText());

                    ps.setString(1,txtnombre.getText());
                    ps.setString(2,txtapellido.getText());
                    ps.setString(3,txtdireccion.getText());
                    ps.setString(4, oper);



                    System.out.println(ps);
                    int res = ps.executeUpdate();

                    if(res > 0 ){
                        JOptionPane.showMessageDialog(null,"La actualización se realizado con EXITO!");
                        txtnombre.setText("");
                        txtapellido.setText("");
                        txtdireccion.setText("");


                    }else{
                        JOptionPane.showMessageDialog(null,"Error, numero no registrado");
                    }
                    connection.close();
                }catch (SQLException f){
                    System.out.println(f);
                }

            }
        });
        btneliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                try {
                    ps = connection.prepareStatement("DELETE FROM usuario WHERE numero ="+txtnumero.getText());
                    int res = ps.executeUpdate();

                    if(res > 0 ){
                        JOptionPane.showMessageDialog(null,"Se elemino con exito");
                    }else{
                        JOptionPane.showMessageDialog(null,"No existe el producto");
                    }
                }catch (HeadlessException | SQLException f){
                    System.out.println(f);
                }
            }
        });
    }

    public static Connection getConection() {
        Connection con = null;
        String base = "agenda";
        String url = "jdbc:mysql://localhost:3306/" + base;
        String user = "root";
        String password = "Luchito2724";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("si se conecto");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println(e);
        }
        return con;


    }
    public static void main(String[] args) {
        JFrame frame=new JFrame("Tienda de abarrotes");
        frame.setContentPane(new agenda().panel1);
        frame.setSize(300,300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
