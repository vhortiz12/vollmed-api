package med.voll.api.domain.medico;

public record DatosListadoMedico(
        Long id,
        String nombre,
        String especialidad,
        String documento,
        String email) {

    public DatosListadoMedico(Medico medico) {
        this(medico.getId(), medico.getNombre(),medico.getEspecialidad().toString(),medico.getDocumento(),medico.getEmail());
    }

    /*En este constructor la especialidad se trae como un String no como un enum, por eso se debe convertir a String con toString*/
}
