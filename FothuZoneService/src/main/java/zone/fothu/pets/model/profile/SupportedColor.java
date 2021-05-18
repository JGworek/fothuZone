package zone.fothu.pets.model.profile;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "supported_user_colors", schema = "pets")
@Accessors(fluent = false, chain = true)
@Data
@NoArgsConstructor
public class SupportedColor implements Serializable {

	private static final long serialVersionUID = -5659595201025851537L;

	@Id
	@Column(name = "colors")
	private String color;
}