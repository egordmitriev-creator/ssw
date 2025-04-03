package io.swagger.model;

import java.util.Objects;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import org.springframework.validation.annotation.Validated;
import io.swagger.configuration.NotUndefined;


import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

/**
 * Comment
 */

@Entity
@Table(name = "comment")

@Validated
@NotUndefined
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2025-03-26T07:34:12.780721949Z[GMT]")


public class Comment   {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "post_id", nullable = false)
  private Long postId;

  @Column(nullable = false, length = 1000)
  private String content;

  @Column(name = "post_date", columnDefinition = "timestamp with time zone")
  private OffsetDateTime postDate;


  public Comment id(Long id) {

    this.id = id;
    return this;
  }

  /**
   * Auto-generated comment ID
   * @return id
   **/

  @Schema(example = "1", accessMode = Schema.AccessMode.READ_ONLY, description = "Auto-generated comment ID")

  public Long getId() {
    return id;
  }



  public void setId(Long id) {
    this.id = id;
  }

  public Comment postId(Long postId) {

    this.postId = postId;
    return this;
  }

  /**
   * ID of the post this comment belongs to
   * @return postId
   **/

  @Schema(example = "1", required = true, description = "ID of the post this comment belongs to")

  @NotNull
  public Long getPostId() {
    return postId;
  }



  public void setPostId(Long postId) {

    this.postId = postId;
  }

  public Comment content(String content) {

    this.content = content;
    return this;
  }

  /**
   * The text content of the comment
   * @return content
   **/

  @Schema(example = "This is a comment", required = true, description = "The text content of the comment")

  @NotNull
@Size(min=1,max=1000)   public String getContent() {
    return content;
  }



  public void setContent(String content) {

    this.content = content;
  }

  public Comment postDate(OffsetDateTime postDate) {

    this.postDate = postDate;
    return this;
  }

  /**
   * Date and time when the comment was posted
   * @return postDate
   **/

  @Schema(example = "2019-01-01T00:00Z", accessMode = Schema.AccessMode.READ_ONLY, description = "Date and time when the comment was posted")

@Valid
  public OffsetDateTime getPostDate() {
    return postDate;
  }



  public void setPostDate(OffsetDateTime postDate) {
    this.postDate = postDate;
  }

  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Comment comment = (Comment) o;
    return Objects.equals(this.id, comment.id) &&
        Objects.equals(this.postId, comment.postId) &&
        Objects.equals(this.content, comment.content) &&
        Objects.equals(this.postDate, comment.postDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, postId, content, postDate);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Comment {\n");

    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    postId: ").append(toIndentedString(postId)).append("\n");
    sb.append("    content: ").append(toIndentedString(content)).append("\n");
    sb.append("    postDate: ").append(toIndentedString(postDate)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
