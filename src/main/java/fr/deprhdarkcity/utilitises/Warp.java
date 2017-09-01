package fr.deprhdarkcity.utilitises;

import com.flowpowered.math.vector.Vector3d;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Warp {
    private Vector3d position;
    private String   worldname;
    private String   name;
    private String   playername;


    public Warp(Vector3d position, String worldname, String name, String playername) {
        this.setPosition(position);
        this.setWorldname(worldname);
        this.setName(name);
        this.setPlayername(playername);

    }
    public String getPlayername() {
        return playername;
    }

    public void setPlayername(String playername) {
        this.playername = playername;
    }

    public Vector3d getPosition() {
        return position;
    }

    public void setPosition(Vector3d position) {
        this.position = position;
    }

    public String getWorldname() {
        return worldname;
    }

    public void setWorldname(String worldname) {
        this.worldname = worldname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Warp warp = (Warp) o;
        return Objects.equals(position , warp.position) &&
               Objects.equals(worldname, warp.worldname) &&
               Objects.equals(name, warp.name) &&
               Objects.equals(playername, warp.playername);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, worldname, name, playername);
    }

    @Override
    public String toString() {
        return "WarpsCreateCommand{" +
               "position=" + position +
               ", worldname='" + worldname + '\'' +
               ", name='" + name + '\'' +
               ", playername='" + playername + '\'' +
               '}';}

    public static Set<Warp> loadAll(Path directory) throws IOException {
            Set<Warp> warp = new HashSet<>();

            for (Path child : Files.newDirectoryStream(directory)) {

              try (BufferedReader reader = Files.newBufferedReader(child)) {
                 warp.add(SpongeUtilities.GSON.fromJson(reader, Warp.class));
            }
        }

        return warp;
    }

    public void save(Path saveDirectory) throws IOException {
        try (BufferedWriter writer =
                     Files.newBufferedWriter(saveDirectory.resolve(String.format("%s.json", this.name)))) {
            SpongeUtilities.GSON.toJson(this, writer);
        }
    }

    public void delete(String filename, Path saveDirectory) throws IOException {
        Files.deleteIfExists(saveDirectory.resolve(String.format("%s.json", filename)));
    }

}
