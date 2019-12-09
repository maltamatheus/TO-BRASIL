package tobrasil.matheusmalta.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tobrasil.matheusmalta.model.Aluno;
import tobrasil.matheusmalta.model.Handler;
import tobrasil.matheusmalta.repository.AlunoRepository;
import tobrasil.matheusmalta.repository.HandlerRepository;

@RestController
@RequestMapping(value = "/")
public class IndexController {

	@Autowired
	private AlunoRepository alunoRepository;

	private HandlerRepository handlerRepository;

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<Aluno> getAluno(@PathVariable(value = "id") Long id){

		Handler handler = new Handler();

		try {

			Optional<Aluno> aluno = alunoRepository.findById(id);
			
			return new ResponseEntity(aluno.get(),HttpStatus.OK);

		} catch (Exception e) {

			Optional<Aluno> aluno = alunoRepository.findById(id);

			handler.setMsgErro(e.getMessage());

			handlerRepository.save(handler);

			return new ResponseEntity(aluno.get(),HttpStatus.NOT_FOUND);
		}
		
	}

	@GetMapping(value = "/todos", produces = "application/json")
	public ResponseEntity<List<Aluno>> getTodos(){

		List<Aluno> alunos = (List<Aluno>) alunoRepository.findAll();

		return new ResponseEntity<List<Aluno>>(alunos,HttpStatus.OK);
	}

	@PostMapping(value = "/cadastro", produces = "application/json")
	public ResponseEntity<Aluno> cadastrar(@RequestBody Aluno aluno){

		Aluno alunoIns = alunoRepository.save(aluno);

		return ResponseEntity.ok(alunoIns);

	}

	@PutMapping(value = "/atualiza/{id}", produces = "application/json")
	public ResponseEntity<Aluno> atualizarCadastro(@RequestBody Aluno aluno, @PathVariable("id") Long id){

		Aluno alunoUpdt = aluno;
		
		Handler handler = new Handler();

		try {
			if (alunoRepository.existsById(id)) {

				alunoUpdt = alunoRepository.save(aluno);
			} 
				
		} catch (Exception e) {
			
			handler.setMsgErro(e.getMessage());

			handlerRepository.save(handler);
			
			return new ResponseEntity<Aluno>(alunoUpdt,HttpStatus.valueOf("Aluno ID = " + id + " não cadastrado"));
		}
		
		return ResponseEntity.ok(alunoUpdt);
	}

	@DeleteMapping(value="/excluir/{id}", produces = "application/text")
	public String excluir(@PathVariable("id") Long id) {

		Long idExcluido = id;

		alunoRepository.deleteById(id);

		return "ID " + idExcluido + " excluído com sucesso";

	}
}
