# Project Wiki

This is a basic developer log intended to briefly write down some of my insights, issues and observations related 
to this project.

---

## Insights
#### Overview
The [README](README.md) guide presents a list of challenges in an specific order and also specifies that each commit
message must follow the SemVer pattern (used to define the application version). Therefore, I assume that a development
focused on generate fewer MAJOR changes throughout the project is the ideal goal here.\
I decide to follow each resolution for each challenge in the exact same  order as described in the [README](README.md).
As if is where a real on-demand task.\
In a real-world scenario, I believe it's a good approach to interpret the final product we're developing as a whole,
rather than just  performing individual tasks one by one as they appear. But since this is a test and there's
no explicit explanation of this practice, I'm encouraged to tackle each challenge one at a time.

#### Liberais
The [README](README.md) guide doesn't make it clear whether I could or could't use to use new dependencies. Therefore, 
I'm assuming its acceptable to add two or three more liberais to the application.

---

## Tasks
### Start Up
- [X] Analise [README](README.md) guide
- [X] Setup project configuration
- [X] Build and run the application locally
- [X] Run all tests
- [X] Analise the given code

### Development
- [X] Create couse entity
- [X] Implement basic service and repository layers for course domain
- [X] Creating course table and then apply each column restriction on every DTO
- [X] Create new JSPs to list all courses and to crate/edit new ones
- [X] Implement logic in [CourseController](src/main/java/br/com/alura/projeto/course/CourseController.java) 
to list all course (JSP)
- [X] Implement logic in [CourseController](src/main/java/br/com/alura/projeto/course/CourseController.java) 
to create a new course (JSP)
- [ ] Implement logic in [CourseController](src/main/java/br/com/alura/projeto/course/CourseController.java) 
to edit a course (JSP)
- [X] Implement logic in [CourseController](src/main/java/br/com/alura/projeto/course/CourseController.java) 
to inactivate a course (JSP)
- [X] Test course controller
  - [X] GET JSP: list all courses
  - [X] GET JSP: create course
  - [X] GET JSP: edit course
  - [X] GET JSP: inactivate course
- [X] Create sql script to insert as many courses as listed in [login.jsp](src/main/webapp/WEB-INF/views/login.jsp)
- [X] Implement [login.jsp](src/main/webapp/WEB-INF/views/login.jsp) endpoint call to get courses (JSP)
- [X] Improve [login.jsp](src/main/webapp/WEB-INF/views/login.jsp) to list some/all courses (JSP)
- [X] Implement logic in the [CategoryController](src/main/java/br/com/alura/projeto/category/CategoryController.java) 
to edit a category (JSP)
- [X] Rename and improve [newForm.jsp](src/main/webapp/WEB-INF/views/admin/category/newForm.jsp) to serve as 
both creation and edition (JSP)
- [X] Improve course's status to be able to activate and inactivate (also in the edition page)  
- [ ] Implement student registration logic in the [RegistrationController](src/main/java/br/com/alura/projeto/registration/RegistrationController.java)
- [ ] Implement registration report logic in the [RegistrationController](src/main/java/br/com/alura/projeto/registration/RegistrationController.java)
- [ ] Fix [login.jsp](src/main/webapp/WEB-INF/views/admin/course/list.jsp) responsiveness (JSP)
- [ ] Change inactivation option in [login.jsp](src/main/webapp/WEB-INF/views/admin/course/list.jsp), from dropdown 
to modal (JSP)
- [ ] Create custom checked-exceptions for each relevant business rule
- [ ] Add javadoc in all methods
- [ ] Encrypt also emails in users and courses

### Extras
- [ ] Create course controller Rest API
  - [ ] GET: to list and filter records
  - [ ] POST: to create a new record
  - [ ] PUT: to update a record
- [ ] Testing the course controller Rest API
  - [ ] GET: without parameters
  - [ ] GET: with valid parameters (match)
  - [ ] GET: with valid parameters (non-match)
  - [ ] GET: with invalid parameter (field length)
  - [ ] GET: with invalid parameter (code patter)
  - [ ] POST: with valid payload
  - [ ] POST: with invalid payload (field length)
  - [ ] POST: with invalid payload (code patter)
  - [ ] POST: with invalid payload (code uniques)
  - [ ] PUT: with valid payload
  - [ ] PUT: with valid payload (data not found)
  - [ ] PUT: with invalid payload (field length)
  - [ ] PUT: with invalid payload (code patter)
  - [ ] PUT: with invalid payload (code uniques)
- [ ] Implement course inactivation Rest API endpoint
- [ ] Implement pagination in [login.jsp](src/main/webapp/WEB-INF/views/admin/course/list.jsp) and [login.jsp](src/main/webapp/WEB-INF/views/admin/category/list.jsp) (JSP)
- [ ] Create in the [list.jsp](src/main/webapp/WEB-INF/views/admin/category/list.jsp) option to link to the JSP
  responsible to list all courses filtering by the current category record (JSP)
- [ ] Test course inactivation Rest API endpoint
  - [ ] PUT: with valid path (match)
  - [ ] PUT: with valid path (non-match)
  - [ ] PUT: with valid path (data not found)
  - [ ] PUT: with invalid path (code patter)
- [ ] Creating new courses should require they respective category
- [ ] Request to initiative a course could receive a description field explaining the reason.
