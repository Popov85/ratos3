package ua.edu.ratos;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import ua.edu.ratos.service.dto.out.report.OrgFacDep;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MapTest {

    @Test
    public void testMap() {
        Map<OrgFacDep, String> map = new HashMap<>();

        OrgFacDep orgFacDep = new OrgFacDep("A", "B", "C");

        map.put(orgFacDep, "My value");

        OrgFacDep orgFacDep2 = new OrgFacDep("A", "B", "C");
        String myValue = map.get(orgFacDep2);
        log.debug("myValue = {}", myValue);
    }
}
