public enum TrackType {
    SMALL_OVAL, LARGE_OVAL, ROAD_COURSE, STREET_COURSE;

    public boolean isOval(){
        return this == SMALL_OVAL || this == LARGE_OVAL;
    }
}
