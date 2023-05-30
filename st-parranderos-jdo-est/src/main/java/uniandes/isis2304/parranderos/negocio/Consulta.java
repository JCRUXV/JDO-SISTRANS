package uniandes.isis2304.parranderos.negocio;

public class Consulta implements VOConsulta {
    private char semana;
    private long operador;
    private long solicitudes;
    
    public Consulta() {
        this.semana = '\0';
        this.operador = 0;
        this.solicitudes = 0;
    }
    
    public Consulta(char semana, long operador, long solicitudes) {
        this.semana = semana;
        this.operador = operador;
        this.solicitudes = solicitudes;
    }
    
    public char getSemana() {
        return semana;
    }
    
    public void setSemana(char semana) {
        this.semana = semana;
    }
    
    public long getOperador() {
        return operador;
    }
    
    public void setOperador(long operador) {
        this.operador = operador;
    }
    
    public long getSolicitudes() {
        return solicitudes;
    }
    
    public void setSolicitudes(long solicitudes) {
        this.solicitudes = solicitudes;
    }
    
    @Override
    public String toString() {
        return "Consulta [semana=" + semana + ", operador=" + operador + ", solicitudes=" + solicitudes + "]";
    }
}
