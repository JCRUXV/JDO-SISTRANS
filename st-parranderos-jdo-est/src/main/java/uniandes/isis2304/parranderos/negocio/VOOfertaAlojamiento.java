package uniandes.isis2304.parranderos.negocio;

public interface VOOfertaAlojamiento {
    /**
     * @return El valor de la semana
     */
    public char getSemana();

    /**
     * @return El valor de la oferta
     */
    public long getOferta();

    /**
     * @return El valor de la ocupación
     */
    public long getOcupacion();

    /**
     * @return Una cadena de caracteres con la información básica de la oferta de alojamiento
     */
    @Override
    public String toString();
}
