package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController //Anotación para que Spring sepa que esto es un controlador
@RequestMapping("/medicos") //Ruta que queremos maperar
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;


    @PostMapping //Anotación para mapear request del tipo POST
    public ResponseEntity<DatosRespuestaMedico> registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico,
                                          UriComponentsBuilder uriComponentsBuilder){//Método para mapear request del tipo POST
        Medico medico = medicoRepository.save(new Medico(datosRegistroMedico));
        DatosRespuestaMedico datosRespuestaMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getEspecialidad().toString(),new DatosDireccion(medico.getDireccion().getCalle(),
                medico.getDireccion().getDistrito(),medico.getDireccion().getCiudad(),medico.getDireccion().getNumero(),
                medico.getDireccion().getComplemento()));
        //Returno 201 Created
        //URL donde encontrar al medico
        //GET http://localhost:8080/medicos/xx
        // URI url = "http://localhost:8080/medicos/" + medico.getId();
        URI url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaMedico);

    }

/* El método creado va a recibir un parámetro que para este ejemplo es un dato de tipo String, que lo que recibirá es un json.
Para que Spring reconozca que este parámetro es el body del request, se debe usar la anotación @RequestBody
*/
    @GetMapping
    public ResponseEntity<Page<DatosListadoMedico>>listadoMedicos(@PageableDefault(size=2) Pageable paginacion){
        //return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
        return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new));
    }

    /*Este método se crea para recibir datos de la API pero restringiendo los campos,es decir, que solo aparezcan los datos
    * que deseamos aparezcan del médico. El método retorna un nuevo objeto de la clase médico, pero con los datos que quere-
    * mos solo aparezcan. Para que esto funcione se debe crear un constructor en el Record DatosListadoMedico
    * */
    @PutMapping
    @Transactional
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizarMedico datosActualizarMedico){
        Medico medico = medicoRepository.getReferenceById(datosActualizarMedico.id());
        medico.actualizarDatos(datosActualizarMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getEspecialidad().toString(),new DatosDireccion(medico.getDireccion().getCalle(),
                medico.getDireccion().getDistrito(),medico.getDireccion().getCiudad(),medico.getDireccion().getNumero(),
                medico.getDireccion().getComplemento())));

    }

    //Delete base datos
//    @DeleteMapping("/{id}")
//    @Transactional
//    public void eliminarMedico(@PathVariable Long id){
//        Medico medico = medicoRepository.getReferenceById(id);
//        medicoRepository.delete(medico);
//    }

    //Delete lógico
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();

    }

    //Este método es para retornar los datos de un médico en específico
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaMedico> retornaDatosMedico(@PathVariable Long id){
        Medico medico = medicoRepository.getReferenceById(id);
        var datosMedico = new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                medico.getTelefono(), medico.getEspecialidad().toString(),new DatosDireccion(medico.getDireccion().getCalle(),
                medico.getDireccion().getDistrito(),medico.getDireccion().getCiudad(),medico.getDireccion().getNumero(),
                medico.getDireccion().getComplemento()));

        return ResponseEntity.ok(datosMedico);

    }

}
