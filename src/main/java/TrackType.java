public enum TrackType {
    SMALL_OVAL, LARGE_OVAL, ROAD_COURSE, STREET_COURSE;

    public boolean isOval(){
        if (this == SMALL_OVAL || this == LARGE_OVAL){
            return true;
        }
        return false;
    }
}
