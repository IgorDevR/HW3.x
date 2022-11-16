package ru.hogwarts.school.controller;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.component.RecordMapper;
import ru.hogwarts.school.entity.Faculty;
import ru.hogwarts.school.record.FacultyRecord;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.service.FacultyService;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = FacultyController.class)
@ExtendWith(MockitoExtension.class)
class FacultyControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private FacultyRepository facultyRepository;

    @SpyBean
    private FacultyService facultyService;

    @SpyBean
    private RecordMapper recordMapper;


    @Test
    void createTest() throws Exception {

        Faculty faculty = new Faculty();
        faculty.setId(1l);
        faculty.setName("griff");
        faculty.setColor("red");

        when(facultyRepository.save(any())).thenReturn(faculty);

        FacultyRecord facultyRecord = new FacultyRecord();
        facultyRecord.setName("griff");
        facultyRecord.setColor("red");

        mockMvc.perform(MockMvcRequestBuilders.post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(facultyRecord)))
                .andExpect(result -> {
                    MockHttpServletResponse mockHttpServletResponse = result.getResponse();
                    FacultyRecord facultyRecordResult = objectMapper.readValue(mockHttpServletResponse.getContentAsString(StandardCharsets.UTF_8), FacultyRecord.class);
                    assertThat(mockHttpServletResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
                    assertThat(facultyRecordResult).isNotNull();
                    assertThat(facultyRecordResult).usingRecursiveComparison().ignoringFields("id").isEqualTo(facultyRecord);
                    assertThat(facultyRecordResult.getId()).isEqualTo(faculty.getId());
                });


    }
}