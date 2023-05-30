package uniandes.isis2304.parranderos.negocio;

public interface VOConsulta {
    /**
     * @return El valor de la semana
     */
    public char getSemana();

    /**
     * @return El valor del operador
     */
    public long getOperador();

    /**
     * @return El valor de las solicitudes
     */
    public long getSolicitudes();

    /**
     * @return Una cadena de caracteres con la información básica de la consulta
     */
    @Override
    public String toString();
}
