package tobrasil.matheusmalta.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tobrasil.matheusmalta.model.Aluno;

@Repository
public interface AlunoRepository extends CrudRepository<Aluno, Long>{

}
