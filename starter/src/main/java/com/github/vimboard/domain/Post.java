package com.github.vimboard.domain;

public class Post {

    private long id;
    private Long thread;
    private String subject;
    private String email;
    private String name;
    private String trip;
    private String capcode;
    private String body;
    private String bodyNomarkup;
    private long time;
    private Long bump;
    private String files;
    private short numFiles;
    private String filehash;
    private String password;
    private String ip;
    private boolean sticky;
    private boolean locked;
    private boolean cycle;
    private boolean sage;
    private String embed;
    private String slug;

    //------------------------------------------------------------------------
    // Getters and builder setters
    //------------------------------------------------------------------------

    public long getId() {
        return id;
    }

    public Post setId(long id) {
        this.id = id;
        return this;
    }

    public Long getThread() {
        return thread;
    }

    public Post setThread(Long thread) {
        this.thread = thread;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public Post setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Post setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public Post setName(String name) {
        this.name = name;
        return this;
    }

    public String getTrip() {
        return trip;
    }

    public Post setTrip(String trip) {
        this.trip = trip;
        return this;
    }

    public String getCapcode() {
        return capcode;
    }

    public Post setCapcode(String capcode) {
        this.capcode = capcode;
        return this;
    }

    public String getBody() {
        return body;
    }

    public Post setBody(String body) {
        this.body = body;
        return this;
    }

    public String getBodyNomarkup() {
        return bodyNomarkup;
    }

    public Post setBodyNomarkup(String bodyNomarkup) {
        this.bodyNomarkup = bodyNomarkup;
        return this;
    }

    public long getTime() {
        return time;
    }

    public Post setTime(long time) {
        this.time = time;
        return this;
    }

    public Long getBump() {
        return bump;
    }

    public Post setBump(Long bump) {
        this.bump = bump;
        return this;
    }

    public String getFiles() {
        return files;
    }

    public Post setFiles(String files) {
        this.files = files;
        return this;
    }

    public short getNumFiles() {
        return numFiles;
    }

    public Post setNumFiles(short numFiles) {
        this.numFiles = numFiles;
        return this;
    }

    public String getFilehash() {
        return filehash;
    }

    public Post setFilehash(String filehash) {
        this.filehash = filehash;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Post setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public Post setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public boolean isSticky() {
        return sticky;
    }

    public Post setSticky(boolean sticky) {
        this.sticky = sticky;
        return this;
    }

    public boolean isLocked() {
        return locked;
    }

    public Post setLocked(boolean locked) {
        this.locked = locked;
        return this;
    }

    public boolean isCycle() {
        return cycle;
    }

    public Post setCycle(boolean cycle) {
        this.cycle = cycle;
        return this;
    }

    public boolean isSage() {
        return sage;
    }

    public Post setSage(boolean sage) {
        this.sage = sage;
        return this;
    }

    public String getEmbed() {
        return embed;
    }

    public Post setEmbed(String embed) {
        this.embed = embed;
        return this;
    }

    public String getSlug() {
        return slug;
    }

    public Post setSlug(String slug) {
        this.slug = slug;
        return this;
    }
}
