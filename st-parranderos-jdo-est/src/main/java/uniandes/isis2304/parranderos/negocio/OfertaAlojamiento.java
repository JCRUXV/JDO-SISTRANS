package uniandes.isis2304.parranderos.negocio;

public class OfertaAlojamiento implements VOOfertaAlojamiento {
    private char semana;
    private long oferta;
    private long ocupacion;

    public OfertaAlojamiento() {
        this.semana = ' ';
        this.oferta = 0;
        this.ocupacion = 0;
    }

    public OfertaAlojamiento(char semana, long oferta, long ocupacion) {
        this.semana = semana;
        this.oferta = oferta;
        this.ocupacion = ocupacion;
    }

    public char getSemana() {
        return semana;
    }

    public void setSemana(char semana) {
        this.semana = semana;
    }

    public long getOferta() {
        return oferta;
    }

    public void setOferta(long oferta) {
        this.oferta = oferta;
    }

    public long getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(long ocupacion) {
        this.ocupacion = ocupacion;
    }

    @Override
    public String toString() {
        return "OfertaAlojamiento [semana=" + semana + ", oferta=" + oferta + ", ocupacion=" + ocupacion + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) oferta;
        result = prime * result + (int) ocupacion;
        result = prime * result + semana;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OfertaAlojamiento other = (OfertaAlojamiento) obj;
        if (oferta != other.oferta)
            return false;
        if (ocupacion != other.ocupacion)
            return false;
        if (semana != other.semana)
            return false;
        return true;
    }
}
