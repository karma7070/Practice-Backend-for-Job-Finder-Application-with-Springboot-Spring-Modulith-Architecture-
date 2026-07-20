package com.FindAJob.demo.jobs.internal;

import com.FindAJob.demo.jobs.JobRequestDTO;
import com.FindAJob.demo.jobs.JobResponseDTO;
import com.FindAJob.demo.jobs.Jobs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JobsService {
    private final JobsRepository repository;
    private final JobsService service;

    @Autowired
    public JobsService(JobsRepository repository, JobsService service){
        this.repository = repository;
        this.service = service;
    }
            ////////////////////////////////////////////
///////////////     END-POINT LOGIC STARTS HERE   /////////////////////////////////////////////////////
            //////////////////////////////////////////


//Return list of jobs

    public List<JobResponseDTO> getAllJobs(){
        List<Jobs> jobs = repository.findAll();

        List<JobResponseDTO> responses = new ArrayList<>();

        for(int i = 0; i < jobs.size(); i++){

            Jobs job_n = jobs.get(i);

            JobResponseDTO response_n = JobResponseDTO.from(job_n);//static 'from()' method used to turn an
            // object (instance of a class) into a data transfer object. It's simply like declaring a function or method but just
            //adding static before the datatype and 'from' instead of a method name (check ResponseDTO)

            responses.add(response_n);

        }

        return responses;
    }

//Add job to DB

    public JobResponseDTO addJob(JobRequestDTO request){
        Jobs job = new Jobs(
                request.job_title(),
                request.description(),
                request.field(),
                request.availability(),
                request.posted_at(),
                request.posted_by()
        );

        repository.save(job);

        JobResponseDTO response = new JobResponseDTO(
                job.getJob_title(),
                job.getDescription(),
                job.getField(),
                job.getAvailability(),
                job.getPosted_at(),
                job.getPosted_by()
        );

        return response;

    }

//Update Job Details


    public JobResponseDTO updateJob(JobRequestDTO request, Long id){
       Optional<Jobs> optionaljob = repository.findById(id);

        //Optional is container which holds instantiated objects and can call methods like
        //.isEmpty() for checking if a specific object in database exists

       if(optionaljob.isEmpty()){
         throw new RuntimeException("Job doesn't exist");
        }

        //since optional<> is a container we have to return 'job'
        // as a 'Jobs' object using '.get()' method to manipulate its data, we can't directly manipulate
        //from inside the container.


        //note: you'll have to make sure fields aren't empty when reassigning values

       Jobs updatedJob = service.checkAndReturn(request,optionaljob.get());

      repository.save(updatedJob);

      return JobResponseDTO.from(updatedJob);
    }












        /////////////////////////////////////////////////////
/////////   Function for checking if request is empty     ////////////////////////////////////////////////
       //////////////////////////////////////////////////////


        public Jobs checkAndReturn(JobRequestDTO request, Jobs job){
//so check for empty JSON with .isBlank() and empty string with null
        if(request.job_title() != null && !request.job_title().isBlank()){
            job.setJob_title(request.job_title());
        }

        if(request.description() != null && !request.description().isBlank()){
            job.setDescription(request.description());
        }

        if(request.field() != null){
            job.setField(request.field());
        }

        if(request.availability() != null){
            job.setAvailability(request.availability());
        }

        if(request.posted_by() != null && !(request.posted_by().isBlank())){
            job.setPosted_by(request.posted_by());
        }

        return job;

    }



}
