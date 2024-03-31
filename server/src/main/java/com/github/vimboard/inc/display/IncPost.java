package com.github.vimboard.inc.display;

import com.github.vimboard.config.settings.VimboardBoardSettings;
import com.github.vimboard.domain.Post;

import java.util.List;
import java.util.Map;
import java.util.regex.PatternSyntaxException;

public class IncPost {

    protected final VimboardBoardSettings config;

    protected long id;
    protected Long thread;
    protected String subject;
    protected String email;
    protected String name;
    protected String trip;
    protected String capcode;
    protected String body;
    protected String bodyNomarkup;
    protected long time;
    protected Long bump;
    protected List<IncFile> files;
    protected short numFiles;
    protected String filehash;
    protected String password;
    protected String ip;
    protected boolean sticky;
    protected boolean locked;
    protected boolean cycle;
    protected boolean sage;
    protected String embed;
    protected String slug;

    protected Map<String, String> modifiers;

    /**
     * TODO: IncThread пока создаётся самостоятельно. Объеденить позже.
     */
    public IncPost(VimboardBoardSettings config) {
        this.config = config;
    }

    public IncPost(VimboardBoardSettings config, Post post) {
        this(config, post, null, false);
    }

    public IncPost(VimboardBoardSettings config, Post post, String root) {
        this(config, post, root, false);
    }

    public IncPost(VimboardBoardSettings config, Post post, String root, boolean mod) {
        this(config);
    }

    public String embedHtml(String link) {
        for (String[] embed : config.getEmbedding()) {
            // TODO: embed[0] - pattern
            // TODO: embed[1] - template
            // TODO: test patterns regexp
            String html;
            try {
                html = link.replaceAll(embed[0], embed[1]);
            } catch (PatternSyntaxException ex) {
                html = null;
            }
            if (html != null) {
                if (link.equals(html)) {
                    continue; // Nope
                }

                html = html.replace("%%tb_width%%", config.getEmbedWidth().toString());
                html = html.replace("%%tb_height%%", config.getEmbedHeight().toString());

                return html;
            }

        }

    	if (link.charAt(0) == '<') {
    		// Prior to v0.9.6-dev-8, HTML code for embedding was stored in the database instead of the link.
    		return link;
    	}

    	return "Embedding error.";
    }

    //------------------------------------------------------------------------
    // Getters and builder setters
    //------------------------------------------------------------------------

    public long getId() {
        return id;
    }

    public IncPost setId(long id) {
        this.id = id;
        return this;
    }

    public Long getThread() {
        return thread;
    }

    public IncPost setThread(Long thread) {
        this.thread = thread;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public IncPost setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public IncPost setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getName() {
        return name;
    }

    public IncPost setName(String name) {
        this.name = name;
        return this;
    }

    public String getTrip() {
        return trip;
    }

    public IncPost setTrip(String trip) {
        this.trip = trip;
        return this;
    }

    public String getCapcode() {
        return capcode;
    }

    public IncPost setCapcode(String capcode) {
        this.capcode = capcode;
        return this;
    }

    public String getBody() {
        return body;
    }

    public IncPost setBody(String body) {
        this.body = body;
        return this;
    }

    public String getBodyNomarkup() {
        return bodyNomarkup;
    }

    public IncPost setBodyNomarkup(String bodyNomarkup) {
        this.bodyNomarkup = bodyNomarkup;
        return this;
    }

    public long getTime() {
        return time;
    }

    public IncPost setTime(long time) {
        this.time = time;
        return this;
    }

    public Long getBump() {
        return bump;
    }

    public IncPost setBump(Long bump) {
        this.bump = bump;
        return this;
    }

    public List<IncFile> getFiles() {
        return files;
    }

    public IncPost setFiles(List<IncFile> files) {
        this.files = files;
        return this;
    }

    public short getNumFiles() {
        return numFiles;
    }

    public IncPost setNumFiles(short numFiles) {
        this.numFiles = numFiles;
        return this;
    }

    public String getFilehash() {
        return filehash;
    }

    public IncPost setFilehash(String filehash) {
        this.filehash = filehash;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public IncPost setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public IncPost setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public boolean isSticky() {
        return sticky;
    }

    public IncPost setSticky(boolean sticky) {
        this.sticky = sticky;
        return this;
    }

    public boolean isLocked() {
        return locked;
    }

    public IncPost setLocked(boolean locked) {
        this.locked = locked;
        return this;
    }

    public boolean isCycle() {
        return cycle;
    }

    public IncPost setCycle(boolean cycle) {
        this.cycle = cycle;
        return this;
    }

    public boolean isSage() {
        return sage;
    }

    public IncPost setSage(boolean sage) {
        this.sage = sage;
        return this;
    }

    public String getEmbed() {
        return embed;
    }

    public IncPost setEmbed(String embed) {
        this.embed = embed;
        return this;
    }

    public String getSlug() {
        return slug;
    }

    public IncPost setSlug(String slug) {
        this.slug = slug;
        return this;
    }

    public Map<String, String> getModifiers() {
        return modifiers;
    }

    public IncPost setModifiers(Map<String, String> modifiers) {
        this.modifiers = modifiers;
        return this;
    }
}
