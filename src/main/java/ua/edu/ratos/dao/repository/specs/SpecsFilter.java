package ua.edu.ratos.dao.repository.specs;

import lombok.Data;

/**
 * This class is adapted to be used with react-bootstrap-tables-2 table project
 * @see <a href="https://github.com/react-bootstrap-table/react-bootstrap-table2">project</a>
 */
@Data
public class SpecsFilter {
    /**
     * 1) String;
     * 2) Foreign key (long);
     * 3) Date object {date, comparator}
     * 4) Number object {number, comparator}
     * Just cast/transform to the appropriate data type according to filterType field
     */
    private Object filterVal;
    private String filterType;
    private String comparator;
    private boolean caseSensitive;
}
