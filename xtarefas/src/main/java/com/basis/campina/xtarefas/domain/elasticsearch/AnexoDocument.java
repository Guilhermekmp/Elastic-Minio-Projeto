package com.basis.campina.xtarefas.domain.elasticsearch;

import com.basis.campina.xtarefas.domain.Tarefa;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;

@Getter
@Document(indexName = "index-anexo")
@NoArgsConstructor
public class AnexoDocument extends BaseDocument{

  @MultiField(mainField = @Field(type = FieldType.Text,
  store = true, analyzer = TRIM_CASE_INSENSITIVE, fielddata = true),
  otherFields = {@InnerField(suffix = SORT, type = FieldType.Text,
  store = true, analyzer = TRIM_CASE_INSENSITIVE, fielddata = true)})
  private String file;

  @MultiField(mainField = @Field(type = FieldType.Text,
          store = true, analyzer = TRIM_CASE_INSENSITIVE, fielddata = true),
          otherFields = {@InnerField(suffix = SORT, type = FieldType.Text,
                  store = true, analyzer = TRIM_CASE_INSENSITIVE, fielddata = true)})
  private String fileName;

  @MultiField(mainField = @Field(type = FieldType.Text,
          store = true, analyzer = TRIM_CASE_INSENSITIVE, fielddata = true),
          otherFields = {@InnerField(suffix = SORT, type = FieldType.Text,
                  store = true, analyzer = TRIM_CASE_INSENSITIVE, fielddata = true)})
  private String uuid;

  @MultiField(mainField = @Field(type = FieldType.Text,
          store = true, analyzer = TRIM_CASE_INSENSITIVE, fielddata = true),
          otherFields = {@InnerField(suffix = SORT, type = FieldType.Text,
                  store = true, analyzer = TRIM_CASE_INSENSITIVE, fielddata = true)})
  private String idTarefa;

  public AnexoDocument(Long id, String file, String fileName, String uuid, Tarefa tarefa) {
    this.id = id;
    this.file = file;
    this.fileName = fileName;
    this.uuid = uuid;
    this.idTarefa = tarefa.getId().toString();
  }
}
