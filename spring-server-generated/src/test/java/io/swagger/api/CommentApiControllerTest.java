package io.swagger.api;

import io.swagger.model.Comment;
import io.swagger.service.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentApiControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentApiController commentApiController;

    @Test
    void getAllComments_ShouldReturnAllComments() {
        // Arrange
        Comment comment1 = createTestComment(1L, 10L, "First comment", OffsetDateTime.now());
        Comment comment2 = createTestComment(2L, 10L, "Second comment", OffsetDateTime.now());

        List<Comment> expectedComments = Arrays.asList(comment1, comment2);
        when(commentService.getAllComments()).thenReturn(expectedComments);

        // Act
        ResponseEntity<List<Comment>> response = commentApiController.getAllComments();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedComments, response.getBody());
        verify(commentService, times(1)).getAllComments();
    }

    @Test
    void createComment_ShouldCreateAndReturnNewComment() {
        // Arrange
        Comment newComment = createTestComment(null, 5L, "New comment", null);

        Comment savedComment = createTestComment(1L, 5L, "New comment", OffsetDateTime.now());
        when(commentService.createComment(newComment)).thenReturn(savedComment);

        // Act
        ResponseEntity<Comment> response = commentApiController.createComment(newComment);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedComment, response.getBody());
        verify(commentService, times(1)).createComment(newComment);
    }

    @Test
    void getCommentById_WithExistingId_ShouldReturnComment() {
        // Arrange
        Long commentId = 1L;
        Comment expectedComment = createTestComment(commentId, 3L, "Test comment", OffsetDateTime.now());

        when(commentService.getCommentById(commentId)).thenReturn(expectedComment);

        // Act
        ResponseEntity<Comment> response = commentApiController.getCommentById(commentId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedComment, response.getBody());
        verify(commentService, times(1)).getCommentById(commentId);
    }

    @Test
    void getCommentById_WithNonExistingId_ShouldReturnNotFound() {
        // Arrange
        Long nonExistingId = 999L;
        when(commentService.getCommentById(nonExistingId)).thenReturn(null);

        // Act
        ResponseEntity<Comment> response = commentApiController.getCommentById(nonExistingId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
        verify(commentService, times(1)).getCommentById(nonExistingId);
    }

    @Test
    void deleteComment_ShouldDeleteCommentAndReturnNoContent() {
        // Arrange
        Long commentId = 1L;
        doNothing().when(commentService).deleteComment(commentId);

        // Act
        ResponseEntity<Void> response = commentApiController.deleteComment(commentId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(commentService, times(1)).deleteComment(commentId);
    }

    private Comment createTestComment(Long id, Long postId, String content, OffsetDateTime postDate) {
        Comment comment = new Comment();
        if (id != null) {
            comment.setId(id);
        }
        comment.setPostId(postId);
        comment.setContent(content);
        if (postDate != null) {
            comment.setPostDate(postDate);
        }
        return comment;
    }
}