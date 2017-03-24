package ua.zp.zsmu.ratos.learning_session.model;

/**
 * Created by Andrey on 24.03.2017.
 */
public class Theme {
        private Long id;
        private String title;
        private Long course;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public Long getCourse() {
                return course;
        }

        public void setCourse(Long course) {
                this.course = course;
        }

        @Override
        public String toString() {
                return "Theme{" +
                        "id=" + id +
                        ", title='" + title + '\'' +
                        ", course=" + course +
                        '}';
        }
}
