package ua.edu.ratos.service.dto.out.report;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class OrgFacDep {
    private String org;
    private String fac;
    private String dep;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrgFacDep orgFacDep = (OrgFacDep) o;
        return Objects.equals(org, orgFacDep.org) &&
                Objects.equals(fac, orgFacDep.fac) &&
                Objects.equals(dep, orgFacDep.dep);
    }

    @Override
    public int hashCode() {
        return Objects.hash(org, fac, dep);
    }
}
