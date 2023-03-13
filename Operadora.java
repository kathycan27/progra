public class Operadora {
    String operadora;
    int indice;

    public Operadora() {
        operadora="";
        indice=0;

    }

    public String getOperadora() {
        return operadora;
    }

    public void setOperadora(String operadora) {
        this.operadora = operadora;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    @Override
    public String toString() {
        return getOperadora();
    }
}
