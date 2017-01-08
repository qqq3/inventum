package org.asdtm.inmovie.provider;



public enum MovieUriEnum {
    MOVIES(100, "movies", MovieContract.Movies.CONTENT_TYPE_ID, false, MovieDatabase.Tables.MOVIES),
    MOVIES_ID(101, "movies/*", MovieContract.Movies.CONTENT_TYPE_ID, true, MovieDatabase.Tables.MOVIES),
    TVS(200, "tvs", MovieContract.TVs.CONTENT_TYPE_ID, false, MovieDatabase.Tables.TVS),
    TVS_ID(201, "tvs/*", MovieContract.TVs.CONTENT_TYPE_ID, true, MovieDatabase.Tables.TVS),
    PERSONS(300, "persons", MovieContract.Persons.CONTENT_TYPE_ID, false, MovieDatabase.Tables.PERSONS),
    PERSONS_ID(301, "persons/*", MovieContract.Persons.CONTENT_TYPE_ID, true, MovieDatabase.Tables.PERSONS);

    public int code;
    public String path;
    public String contentType;
    public String table;

    MovieUriEnum(int code, String path, String contentTypeId, boolean item, String table) {
        this.code = code;
        this.path = path;
        this.contentType = item ? MovieContract.makeContentItemType(contentTypeId)
                : MovieContract.makeContentType(contentTypeId);
        this.table = table;
    }
}
