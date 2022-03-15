package com.da.controller;

import com.da.service.DocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DocumentController.class)
public class DocumentControllerTest {

    @InjectMocks
    private DocumentController documentController;

    @MockBean
    private DocumentService documentService;

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    public void setup(){
        this.documentController = new DocumentController();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUploadDocument() throws Exception {
        byte[] content = null;
        MockMultipartFile file = new MockMultipartFile("document",
                "document", "text/plain", content);
        MockHttpServletRequestBuilder builder = multipart("/document")
                .file(file)
                .param("userEmail", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        this.mockMvc.perform(builder).andExpect(status().isOk());
        verify(this.documentService).uploadDocument(file, "123");
    }

    @Test
    public void testUploadDocumentBadRequest() throws Exception {
        byte[] content = null;
        MockMultipartFile file = new MockMultipartFile("document",
                "document", "text/plain", content);
        MockHttpServletRequestBuilder builder = multipart("/document")
                .file(file)
                .param("BadRequest", "123")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);
        this.mockMvc.perform(builder).andExpect(status().isBadRequest());
    }


    @Test
    public void testGetAllDocumentsByUser() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/document/documents")
                .accept(MediaType.APPLICATION_JSON)
                .queryParam("userEmail", "123");
        this.mockMvc.perform(builder).andExpect(status().isOk());
        verify(this.documentService).getAllDocumentsByUser("123");
    }

    @Test
    public void testGetAllDocumentsByUserBadRequest() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/document/documents")
                .accept(MediaType.APPLICATION_JSON)
                .queryParam("badRequest", "123");
        this.mockMvc.perform(builder).andExpect(status().isBadRequest());
    }

    @Test
    public void testGetFrecuentWords() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/document/frecuency")
                .accept(MediaType.APPLICATION_JSON)
                .queryParam("totalWords", String.valueOf(3))
                .queryParam("userEmail", "123")
                .queryParam("documentName", "document");
        this.mockMvc.perform(builder).andExpect(status().isOk());
        verify(this.documentService).getFrecuentWords(3, "123", "document");
    }

    @Test
    public void testGetFrecuentWordsBadRequest() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/document/frecuency")
                .accept(MediaType.APPLICATION_JSON)
                .queryParam("totalWords", String.valueOf(3))
                .queryParam("BadRequest", "123")
                .queryParam("documentName", "document");
        this.mockMvc.perform(builder).andExpect(status().isBadRequest());
    }

    @Test
    public void testGetUsersNotUpload() throws Exception {
        String fromDate = "2022-03-11";
        String toDate = "2022-03-14";
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/document/users")
                .accept(MediaType.APPLICATION_JSON)
                .queryParam("fromDate", fromDate)
                .queryParam("toDate", toDate);
        this.mockMvc.perform(builder).andExpect(status().isOk());
        verify(this.documentService).getUsersNotUpload(fromDate, toDate);
    }

    @Test
    public void testGetUsersNotUploadBadRequest() throws Exception {
        MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
                .get("/document/users")
                .accept(MediaType.APPLICATION_JSON)
                .queryParam("BadRequest", "2022-03-11")
                .queryParam("toDate", "2022-03-14");
        this.mockMvc.perform(builder).andExpect(status().isBadRequest());
    }


}
