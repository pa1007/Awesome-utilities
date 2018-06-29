package fr.depthdarkcity.sponge_utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.depthdarkcity.sponge_utilities.command.Command;
import fr.depthdarkcity.sponge_utilities.command.ban.BanCommand;
import fr.depthdarkcity.sponge_utilities.command.ban.TempBanCommand;
import fr.depthdarkcity.sponge_utilities.command.broadcoast.BroadcastCommand;
import fr.depthdarkcity.sponge_utilities.command.debug.DebugCommand;
import fr.depthdarkcity.sponge_utilities.command.fly.FlyCommand;
import fr.depthdarkcity.sponge_utilities.command.freeze.FreezeCommand;
import fr.depthdarkcity.sponge_utilities.command.god.GodCommand;
import fr.depthdarkcity.sponge_utilities.command.god.UnGodEveryoneCommand;
import fr.depthdarkcity.sponge_utilities.command.hat.HatCommand;
import fr.depthdarkcity.sponge_utilities.command.killall.KillAllCommand;
import fr.depthdarkcity.sponge_utilities.command.ping.PingCommand;
import fr.depthdarkcity.sponge_utilities.command.speed.SpeedCommand;
import fr.depthdarkcity.sponge_utilities.command.staffChat.StaffChatCommand;
import fr.depthdarkcity.sponge_utilities.command.stop.StopCommand;
import fr.depthdarkcity.sponge_utilities.command.sword.SwordGiveCommand;
import fr.depthdarkcity.sponge_utilities.command.teleportation.InterdimentionalTeleportationCommand;
import fr.depthdarkcity.sponge_utilities.command.teleportation.TeleportationToAll;
import fr.depthdarkcity.sponge_utilities.command.vanish.VanishCommand;
import fr.depthdarkcity.sponge_utilities.command.vote.VoteCommand;
import fr.depthdarkcity.sponge_utilities.command.warn.WarnCommand;
import fr.depthdarkcity.sponge_utilities.command.warp.WarpCommand;
import fr.depthdarkcity.sponge_utilities.creator.Warn;
import fr.depthdarkcity.sponge_utilities.creator.Warp;
import fr.depthdarkcity.sponge_utilities.exepetion.InvalidUserNameException;
import fr.depthdarkcity.sponge_utilities.exepetion.InvalidUserUUIDException;
import fr.depthdarkcity.sponge_utilities.listener.Listener;
import fr.depthdarkcity.sponge_utilities.listener.chat_listener.ChatListner;
import fr.depthdarkcity.sponge_utilities.listener.connection_listener.ConnectionListener;
import fr.depthdarkcity.sponge_utilities.listener.entity_listener.DamageListener;
import fr.depthdarkcity.sponge_utilities.listener.interation_listener.InteractionListener;
import fr.depthdarkcity.sponge_utilities.listener.moving_listener.MovingListener;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.event.game.GameReloadEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import javax.inject.Inject;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Plugin(id = "sponge_utilities",
        name = "SpongeUtilities",
        version = "2.5.3.1",
        url = "http://pa1007.cloud4you.fr/",
        description = "Brings to you some Useful utilities in minecraft sponge and designed to be the dependency of a discord bot ",
        authors = {"pa1007"})
public class SpongeUtilities {

    private static final Gson                         GSON      =
            new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    private static final Set<UUID>                    godded    = new HashSet<>();
    private static       SpongeUtilities              pluginInstance;
    private static       Optional<UserStorageService> userStorage;
    public final         Map<UUID, List<Warn>>        warns;
    private              String                       voteMessage;
    private final        Set<UUID>                    debugList = new HashSet<>();
    private final        Set<UUID>                    frozedPlayer;
    @Inject
    @ConfigDir(sharedRoot = false)
    private              Path                         configPath;
    @Inject
    private              Logger                       logger;
    private final        Command[]                    commands;
    private final        Listener[]                   events;
    private final        Map<String, Warp>            warps;
    private final        Map<String, Integer>         choices;
    private              Boolean                      closed;
    private              List<String>                 listEntityUUID;
    private              Boolean                      deletable;
    private final        Set<UUID>                    voter;


    public SpongeUtilities() {
        this.events = new Listener[]{
                new ConnectionListener(this),
                new ChatListner(this),
                new MovingListener(this),
                new InteractionListener(this),
                new DamageListener(this)
        };

        this.commands = new Command[]{
                new InterdimentionalTeleportationCommand(this),
                new BanCommand(this),
                new TempBanCommand(this),
                new WarnCommand(this),
                new BroadcastCommand(this),
                new WarpCommand(this),
                new SpeedCommand(this),
                new TeleportationToAll(this),
                new StopCommand(this),
                new HatCommand(this),
                new VoteCommand(this),
                new GodCommand(this),
                new UnGodEveryoneCommand(this),
                new VanishCommand(this),
                new StaffChatCommand(this),
                new FlyCommand(this),
                new PingCommand(this),
                //new StateBlockCommand(this),
                new DebugCommand(this),
                new SwordGiveCommand(this),
                new KillAllCommand(this),
                new FreezeCommand(this)
        };

        this.warps = new HashMap<>();
        this.warns = new HashMap<>();
        this.choices = new HashMap<>();
        this.deletable = Boolean.FALSE;
        this.closed = Boolean.FALSE;
        this.voter = new HashSet<>();
        this.listEntityUUID = new ArrayList<>();
        this.listEntityUUID.add(EntityTypes.CREEPER.getId());
        this.listEntityUUID.add(EntityTypes.ENDERMAN.getId());
        this.listEntityUUID.add(EntityTypes.ZOMBIE.getId());
        this.listEntityUUID.add(EntityTypes.SKELETON.getId());
        this.listEntityUUID.add(EntityTypes.SPIDER.getId());
        this.listEntityUUID.add(EntityTypes.CAVE_SPIDER.getId());
        this.listEntityUUID.add(EntityTypes.ENDERMITE.getId());
        this.listEntityUUID.add(EntityTypes.PIG_ZOMBIE.getId());
        this.listEntityUUID.add(EntityTypes.WITCH.getId());
        this.listEntityUUID.add(EntityTypes.SILVERFISH.getId());
        pluginInstance = this;
        this.frozedPlayer = new HashSet<>();
    }

    /**
     * @return a Set of UUID for the players who are froze
     */
    public Set<UUID> getFrozePlayer() {
        return frozedPlayer;
    }

    /**
     * @return a Set of UUID for the players who have god power
     */
    public Set<UUID> getGodded() {
        return godded;
    }

    /**
     * @return a map with Vote info and Vote result
     */
    public Map<String, Integer> getChoices() {
        return this.choices;
    }

    /**
     * @return a Map with Warp Name and The {@link fr.depthdarkcity.sponge_utilities.creator.Warp Warp} info
     */
    public Map<String, Warp> getWarps() {
        return this.warps;
    }

    /**
     * @return a Map with UUID of player warn and a list of {@link fr.depthdarkcity.sponge_utilities.creator.Warn Warn}
     */
    public Map<UUID, List<Warn>> getWarn() {
        return this.warns;
    }

    /**
     * @return File Location where {@link fr.depthdarkcity.sponge_utilities.creator.Warp Warp} are stored
     */
    public Path getWarpsFile() {
        return this.configPath.toAbsolutePath().normalize().resolve("warps.json");
    }

    /**
     * @return File Location where {@link fr.depthdarkcity.sponge_utilities.creator.Warn Warn} are stored
     */
    public Path getWarnsFile() {
        return this.configPath.toAbsolutePath().normalize().resolve("warn.json");
    }

    @org.spongepowered.api.event.Listener
    public void reload(GameReloadEvent event) {
        event.getCause().first(Player.class).get().sendMessage(Text.of("Relaoding Server"));
        for (UUID playerUUID : getDebugList()) {
            debugInChatMessage(Sponge.getServer().getPlayer(playerUUID).get(), Text.of("ReLoading Server !"));
        }
        this.saveWarps();
        this.saveWarns();
        this.loadWarps();
        this.loadWarns();
        this.deleteVote();
    }

    @org.spongepowered.api.event.Listener
    public void preInit(GamePreInitializationEvent evt) {
        this.loadWarps();
        this.loadWarns();
        this.deleteVote();
        this.logger.debug("Registering commands...");

        for (Command command : this.commands) {
            this.logger.trace("Registering command {}", String.join("/", command.getNames()));

            Sponge.getCommandManager().register(this, command.createCommand(), command.getNames());
        }
        for (Listener event : this.events) {
            this.logger.trace("Registering event {}", String.join("/", event.getEventName()));

            Sponge.getEventManager().registerListeners(this, event);
        }

        this.logger.debug("Registered {} commands.", this.commands.length);
        this.logger.debug("Registered {} events.", this.events.length);
        userStorage = Sponge.getServiceManager().provide(UserStorageService.class);

    }

    /**
     * to save warps after creating one
     */
    public void saveWarps() {
        Path warpsFile = this.getWarpsFile();

        try {
            this.logger.info("Saving warps...");

            Path parentDir = warpsFile.getParent();

            if (!Files.isDirectory(parentDir)) {
                Files.createDirectories(parentDir);
            }

            try (Writer writer = Files.newBufferedWriter(warpsFile)) {
                GSON.toJson(this.warps.values(), writer);
            }

            this.logger.info("Successfully saved {} warps!", this.warps.size());
        }
        catch (IOException e) {
            this.logger.error("Unable to save warps:", e);
        }
    }

    /**
     * To load warps
     */
    public void loadWarps() {
        Path warpsFile = this.getWarpsFile();

        this.logger.info("Loading warps...");

        if (Files.notExists(warpsFile)) {
            this.logger.info("No warp loaded since the file containing the warps does not exists!");
            return;
        }

        try (Reader reader = Files.newBufferedReader(warpsFile)) {
            Warp[] warps = GSON.fromJson(reader, Warp[].class);

            this.warps.putAll(Arrays.stream(warps).collect(Collectors.toMap(Warp::getName, Function.identity())));
        }
        catch (IOException e) {
            this.logger.error("Unable to load warps:", e);
        }
    }

    /**
     * To save warns after adding one
     */
    public void saveWarns() {
        Path warnFile = this.getWarnsFile();

        try {
            this.logger.info("Creating a new warn...");

            Path parentDir = warnFile.getParent();

            if (!Files.isDirectory(parentDir)) {
                Files.createDirectories(parentDir);
            }

            try (Writer writer = Files.newBufferedWriter(warnFile)) {
                GSON.toJson(this.warns.values().stream().flatMap(List::stream).toArray(), writer);
            }

            this.logger.info("Successfully creating a new warn!");
        }
        catch (IOException e) {
            this.logger.error("Unable to create warn:", e);
        }
    }

    /**
     * To load the warn for getting the warns loaded
     */
    public void loadWarns() {
        Path warnsFile = this.getWarnsFile();

        this.logger.info("Loading warns...");

        if (Files.notExists(warnsFile)) {
            this.logger.info("No warn loaded since the file containing the warns does not exists!");
            return;
        }

        try (Reader reader = Files.newBufferedReader(warnsFile)) {
            Warn[] warnsList = GSON.fromJson(reader, Warn[].class);

            for (Warn warn : warnsList) {
                List<Warn> warns = this.warns.computeIfAbsent(warn.getPlayerUUID(), uuid -> new ArrayList<>());

                warns.add(warn);
            }

            this.logger.info("{} warns loaded successfully!", warnsList.length);
        }
        catch (IOException e) {
            this.logger.error("Unable to load warns:", e);
        }
    }

    /**
     * to init vote after result been seen
     */
    public void deleteVote() {
        setDeletable(false);
        setClosed(false);
        voter.clear();
        choices.clear();
        setVoteMessage(null);
    }

    /**
     * @return if the vote is deletable
     */
    public boolean getDeletable() {
        return deletable;
    }

    /**
     * @param deletable {@link java.lang.Boolean Boolean} set the vote deletable or not
     */
    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    /**
     * @return the vote situation
     */
    public boolean getClosed() {
        return closed;
    }

    /**
     * to close or open the vote (open only on voteInit)
     *
     * @param closed {@link java.lang.Boolean boolean} <br> false = close <br>
     *               true = open
     */
    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    /**
     * @return Set of UUID of player who have voted to a vote
     */
    public Set<UUID> getVoter() {
        return voter;
    }

    /**
     * List of Entity who are main monster
     *
     * @return list of Entity id
     */
    public List<String> getListEntity() {
        return listEntityUUID;
    }

    /**
     * To get the list of Player in UUID form who can see the debug info
     *
     * @return a Set of UUID
     */
    public Set<UUID> getDebugList() {
        return debugList;
    }

    /**
     * To send message to player in debug List for error who also go to the console
     *
     * @param player The {@link org.spongepowered.api.entity.living.player.Player Player} who will receive the Message
     * @param text   {@link org.spongepowered.api.text.Text Text} to send to the player can be {@code Text.of("String" , Exception)}
     */
    public void debugInChatMessage(Player player, Text text) {
        player.sendMessage(Text.of(TextColors.RED, "[Debug]", TextColors.RESET, text));
    }

    /**
     * To get the warn list of a player by is UUID
     *
     * @param uuid player UUID
     * @return {@link fr.depthdarkcity.sponge_utilities.creator.Warn Warn} or {@link java.lang.ref.ReferenceQueue.Null null}
     * @throws InvalidUserUUIDException given for an invalid or unknown UUID
     * @throws NullPointerException     given by none warn for the player
     */
    public List<Warn> playerWarnUUID(UUID uuid) throws InvalidUserUUIDException, NullPointerException {
        if (!getUserByUUID(uuid).isPresent()) {
            throw new InvalidUserUUIDException();
        }
        return warns.get(uuid);
    }

    /**
     * to get the warn list of a player by is Name
     *
     * @param playerName player name
     * @return {@link fr.depthdarkcity.sponge_utilities.creator.Warn Warn} or {@link java.lang.ref.ReferenceQueue.Null null}
     * @throws NullPointerException     given by none warn for the player
     * @throws InvalidUserNameException given for an invalid or unknown Name
     */
    public List<Warn> playerWarnName(String playerName) throws NullPointerException, InvalidUserNameException {
        Optional<User> user = getUserByName(playerName);
        if (!user.isPresent()) {
            throw new InvalidUserNameException();
        }
        return warns.get(user.get().getUniqueId());
    }

    /**
     * @return {@link String} the all message of the vote , send null if no vote send
     */
    public String getVoteMessage() {
        return voteMessage;
    }

    /**
     * Set a new vote with auto open
     *
     * @param voteMessage {@link String} the vote , with choices
     */
    public void setVoteMessage(String voteMessage) {
        this.voteMessage = voteMessage;
        this.closed = false;
    }

    /**
     * Get the spongeUtilities logger
     *
     * @return the spongeUtilities logger
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * @return {@link fr.depthdarkcity.sponge_utilities.SpongeUtilities SpongeUtilities} The plugin instance
     */
    public static SpongeUtilities getPluginInstance() {
        return pluginInstance;
    }

    /**
     * To get an offline user by is UUID
     *
     * @param uuid The UUID fo the player
     * @return {@link org.spongepowered.api.entity.living.player.User User}
     */
    public static Optional<User> getUserByUUID(UUID uuid) {
        return userStorage.get().get(uuid);
    }

    /**
     * To get an offline user by is Last Name
     *
     * @param name The name of the player
     * @return {@link org.spongepowered.api.entity.living.player.User User}
     */
    public static Optional<User> getUserByName(String name) {
        return userStorage.get().get(name);
    }

    /**
     * To broadcast a {@link org.spongepowered.api.text.Text Text} to all the player and the console
     *
     * @param text {@link org.spongepowered.api.text.Text Text} to broadcast a sponge text like {@code Text.of("String")}
     * @return {@link org.spongepowered.api.text.Text Text} ; The text given and the header [Broadcast] :
     */
    public static Text broadcast(Text text) {
        Text main = Text.of(
                TextColors.RED, "[Broadcast] : ", TextColors.RESET,
                text
        );
        Sponge.getGame().getServer().getBroadcastChannel().send(main);
        return main;
    }
}