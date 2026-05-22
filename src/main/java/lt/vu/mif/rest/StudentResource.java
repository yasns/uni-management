package lt.vu.mif.rest;

import lt.vu.mif.entities.Student;
import lt.vu.mif.persistence.StudentDao;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
public class StudentResource {

    @Inject
    private StudentDao studentDao;

    @GET
    public List<Student> getAll() {
        return studentDao.listAll();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") Integer id) {
        Student s = studentDao.findById(id);
        return s != null ? Response.ok(s).build() : Response.status(Status.NOT_FOUND).build();
    }

    @POST
    @Transactional
    public Response create(Student student) {
        studentDao.persist(student);
        return Response.status(Status.CREATED).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Integer id, Student incoming) {
        Student existing = studentDao.findById(id);
        if (existing == null) return Response.status(Status.NOT_FOUND).build();
        existing.setFirstName(incoming.getFirstName());
        existing.setLastName(incoming.getLastName());
        existing.setEmail(incoming.getEmail());
        studentDao.update(existing);
        return Response.ok(existing).build();
    }
}
