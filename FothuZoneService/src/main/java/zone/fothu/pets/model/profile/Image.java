package zone.fothu.pets.model.profile;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Component
@Scope("prototype")
@Entity
@Table(name = "images", schema = "pets")
@Accessors(fluent = false, chain = true)
@Data
@NoArgsConstructor
public class Image implements Serializable {

	private static final long serialVersionUID = 1279603954667668706L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "image_url")
	private String imageURL;
}