package com.FindAJob.demo.jobs.internal;

import com.FindAJob.demo.companies.internal.CompService;
import com.FindAJob.demo.companies.Companies;
import com.FindAJob.demo.jobs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class JobsService {
    private final JobsRepository repository;
    private final CompService compservice;
    private final ApplicationEventPublisher publisher;

    @Autowired
    public JobsService(JobsRepository repository, CompService compservice, ApplicationEventPublisher publisher){
        this.repository = repository;
        this.compservice = compservice;
        this.publisher = publisher;
    }
            ////////////////////////////////////////////
///////////////     END-POINT LOGIC STARTS HERE   /////////////////////////////////////////////////////
            //////////////////////////////////////////


//Return list of jobs

    public List<JobResponseDTO> getAllJobs(){

        List<Jobs> jobs = repository.findAll();

        List<JobResponseDTO> responses = this.getList(jobs);

     return responses;
    }

//Return jobs by specific company

    public List<JobResponseDTO> getJobsByCompany() {

        String err = Objects.requireNonNull
                        (SecurityContextHolder
                                .getContext()
                                .getAuthentication())
                .getName() + "No homo";

        Optional<Companies> comp = compservice.getUserByEmail
                (Objects.requireNonNull
                        (SecurityContextHolder
                        .getContext()
                        .getAuthentication())
                .getName());


        if(comp.isPresent()){
             Companies comp2 = comp.get();
         } else{

             throw new UsernameNotFoundException("Company not found" + err);
         }

            List<Jobs> job =
                    repository.findByCompany_id(comp.get().getId());

            if (!(job.isEmpty())) {

                ArrayList<JobResponseDTO> responses = new ArrayList<>();

                for (int i = 0; i < job.size(); i++) {

                    Jobs job_n = job.get(i);

                    responses.add(JobResponseDTO.from(job_n));
                }

                return responses;

            } else {

                throw new UsernameNotFoundException("No jobs created yet");

            }

    }

// Get job by Id
    public JobResponseDTO getAJob(Long id) {

       Optional<Jobs> job = repository.findById(id);

       if (job.isPresent()) {

           return JobResponseDTO.from(job.get());

       }
             return null;
   }

//Add job to DB (for companies)

    public JobResponseDTO addJob(JobRequestDTO request){

         Companies comp = compservice.getCompById(request.compId());

            if(comp == null){
                throw new UsernameNotFoundException("Company not Found");
            }

                Jobs job = new Jobs(
                        request.job_title(),
                        request.description(),
                        request.salary(),
                        request.field(),
                        request.availability(),
                        Instant.now(),
                        comp.getComp_name(),
                        comp
                );

                   // job.setPosted_by(comp.getComp_name());

                     repository.save(job);

                        JobCreatedEvent event = new JobCreatedEvent(job.getId(),
                                job.getJob_title(),
                                comp.getCompEmail());

                        publisher.publishEvent(event);

                            JobResponseDTO response = new JobResponseDTO(
                                    job.getJob_title(),
                                    job.getDescription(),
                                    job.getSalary(),
                                    job.getField(),
                                    job.getAvailability(),
                                    job.getPosted_at(),
                                    job.getPosted_by(),
                                    job.getCompany().getId()
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

                         Jobs updatedJob = this.checkAndReturn(request,optionaljob.get());

                         repository.save(updatedJob);

      return JobResponseDTO.from(updatedJob);

    }
    
//Delete job

    public JobResponseDTO deleteJob(Long id){

        Optional<Jobs> job = repository.findById(id);

            if(!(job.isEmpty())) {

                JobDeletedEvent event = new JobDeletedEvent(job.get().getId(), job.get().getJob_title());

                publisher.publishEvent(event);

                repository.deleteById(id);
            }

        return null;
    }











        /////////////////////////////////////////////////////
/////////   Function for checking if request is empty     ////////////////////////////////////////////////
       //////////////////////////////////////////////////////



        public Jobs createCheck(JobRequestDTO request, Jobs job){
//so check for empty JSON with .isBlank() and empty string with null
        if((request.job_title() != null && !request.job_title().isBlank())
                && (request.description() != null && !request.description().isBlank())
                && (request.field() != null)
                && (request.availability() != null)){
            job.setJob_title(request.job_title());
            job.setDescription(request.description());
            job.setField(request.field());
            job.setAvailability(request.availability());

        } else {
            throw new RuntimeException("Fill all Fields (A field is empty)");
        }

        return job;

    }

    // ////////////////////update function here

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

        return job;

    }


//Returns Arraylist of users to the response

    public List<JobResponseDTO> getList(List<Jobs> jobs){

    List<JobResponseDTO> responses = new ArrayList<>();

     for(int i = 0; i<jobs.size(); i++){

         Jobs job = jobs.get(i);

         responses.add(
                 new JobResponseDTO(
                         job.getJob_title(),
                         job.getDescription(),
                         job.getSalary(),
                         job.getField(),
                         job.getAvailability(),
                         job.getPosted_at(),
                         job.getPosted_by(),
                         job.getCompany().getId()
                 )

         );
     }

     return responses;

    }

    public Jobs getJob(Long id){
     return repository.findById(id).
             orElseThrow(() -> new RuntimeException("Job not found"));
    };
         //////////////////////////
/////////////////   DRAFT   ////////////////////////////
          ////////////////////////

    /*
        for(int i = 0; i < jobs.size(); i++){
            Jobs job_n = jobs.get(i);
            JobResponseDTO response_n = JobResponseDTO.from(job_n);//static 'from()' method used to turn an
            // object (instance of a class) into a data transfer object. It's simply like declaring a function or method but just
            //adding static before the datatype and 'from' instead of a method name (check ResponseDTO)
            responses.add(response_n);
        }
*/

}
