package io.swagger.service;

import io.swagger.model.Comment;
import io.swagger.repository.CommentRepository;
import io.swagger.api.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentService commentService;

    private Comment testComment;

    @BeforeEach
    void setUp() {
        testComment = new Comment()
                .id(1L)
                .postId(10L)
                .content("Test comment content")
                .postDate(OffsetDateTime.now());
    }

    @Test
    void createComment_ShouldSuccessfullyCreateComment() {
        when(commentRepository.save(any(Comment.class))).thenReturn(testComment);

        Comment result = commentService.createComment(testComment);

        assertNotNull(result);
        assertEquals(testComment.getId(), result.getId());
        assertEquals(testComment.getContent(), result.getContent());
        verify(commentRepository, times(1)).save(testComment);
    }

    @Test
    void createComment_ShouldThrowException_WhenContentIsEmpty() {
        testComment.setContent("");

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> commentService.createComment(testComment));

        assertEquals("Comment content cannot be empty", exception.getMessage());
        verify(commentRepository, never()).save(any());
    }

    @Test
    void createComment_ShouldThrowException_WhenContentIsNull() {
        testComment.setContent(null);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> commentService.createComment(testComment));

        assertEquals("Comment content cannot be empty", exception.getMessage());
        verify(commentRepository, never()).save(any());
    }

    @Test
    void getAllComments_ShouldReturnEmptyList_WhenNoCommentsExist() {
        when(commentRepository.findAll()).thenReturn(Collections.emptyList());

        List<Comment> result = commentService.getAllComments();

        assertTrue(result.isEmpty());
        verify(commentRepository, times(1)).findAll();
    }

    @Test
    void getAllComments_ShouldReturnAllComments() {
        List<Comment> expectedComments = Arrays.asList(
                testComment,
                new Comment().id(2L).postId(10L).content("Another comment")
        );
        when(commentRepository.findAll()).thenReturn(expectedComments);

        List<Comment> result = commentService.getAllComments();

        assertEquals(2, result.size());
        verify(commentRepository, times(1)).findAll();
    }

    @Test
    void getCommentById_ShouldReturnComment_WhenExists() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(testComment));

        Comment result = commentService.getCommentById(1L);

        assertNotNull(result);
        assertEquals(testComment.getId(), result.getId());
        verify(commentRepository, times(1)).findById(1L);
    }

    @Test
    void getCommentById_ShouldThrowNotFoundException_WhenNotExists() {
        when(commentRepository.findById(99L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> commentService.getCommentById(99L));

        assertEquals("Comment not found with ID: 99", exception.getMessage());
        verify(commentRepository, times(1)).findById(99L);
    }

    @Test
    void deleteComment_ShouldSuccessfullyDeleteComment() {
        when(commentRepository.existsById(1L)).thenReturn(true);
        doNothing().when(commentRepository).deleteById(1L);

        commentService.deleteComment(1L);

        verify(commentRepository, times(1)).existsById(1L);
        verify(commentRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteComment_ShouldThrowNotFoundException_WhenNotExists() {
        when(commentRepository.existsById(99L)).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> commentService.deleteComment(99L));

        assertEquals("Comment not found with ID: 99", exception.getMessage());
        verify(commentRepository, times(1)).existsById(99L);
        verify(commentRepository, never()).deleteById(any());
    }

    @Test
    void createComment_ShouldThrowException_WhenRepositoryFails() {
        when(commentRepository.save(any(Comment.class))).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> commentService.createComment(testComment));

        assertEquals("Failed to create comment", exception.getMessage());
        verify(commentRepository, times(1)).save(testComment);
    }
}