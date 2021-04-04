package com.biachacon.tasks.repositories;

import com.biachacon.tasks.domains.Task;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM TASK WHERE STATUS = true", nativeQuery = true)
    void deleteAllStatusCompleted();

    @Query(value = "SELECT * FROM TASK WHERE ID = ?1 OR TITLE = ?2 OR DESCRIPTION = ?3 OR RESPONSIBLE = ?4 OR STATUS = ?5", nativeQuery = true)
    List<Task> find(Integer id, String title, String description, String responsible, Boolean status);

    @Query(value = "SELECT * FROM TASK WHERE STATUS = ?1", nativeQuery = true)
    List<Task> findByStatus(Boolean b);


}