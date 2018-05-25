package ua.edu.ratos.domain.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;
@Setter
@Getter
public class Help {
    private long helpId;
    private String help;
    private Optional<Resource> resource;
}
