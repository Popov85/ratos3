package ua.edu.ratos.service.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Optional;

@Getter
@Setter
@ToString(exclude = {"resourceDomain"})
@Accessors(chain = true)
public class HelpDomain implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long helpId;

    private String name;

    private String help;

    @JsonProperty("resourceDomain")
    private ResourceDomain resourceDomain;

    // ResourceDomain may or may not be present, most often it is not present
    @JsonIgnore
    public Optional<ResourceDomain> getResourceDomain() {
        return Optional.ofNullable(resourceDomain);
    }

}
