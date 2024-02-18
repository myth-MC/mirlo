# mirlo 🐦‍⬛

### A simple approach to [plugin messaging](https://web.archive.org/web/20220711204310/https://dinnerbone.com/blog/2012/01/13/minecraft-plugin-channels-messaging/)

<details open="open">
  <summary>Table of contents</summary>
  <ol>
    <li>
      <a href="#information">Information</a>
    </li>
    <li>
      <a href="#installation">Installation</a>
    </li>
    <li>
      <a href="#usage">Usage</a>
    </li>
  </ol>
</details>

<div id="information"></div>

## Information

**mirlo 🐦‍⬛** provides server owners with a simple way of implementing and handling plugin messaging between a proxy and its backends (check [Usage](#usage)).

>[!WARNING]
> mirlo is **not ready for production use** yet. The project is still missing an important part of its core functions. You can report any misbehaviours or share any feedback by [creating an issue](https://github.com/myth-MC/mirlo/issues). 

### Compatibility chart

|                                                         | Compatible? | Version |
|---------------------------------------------------------|-------------|---------|
| [Velocity](https://papermc.io/software/velocity)        | ❌          |         |
| [BungeeCord](https://www.spigotmc.org/wiki/bungeecord/) | ❌          |         |
| [Paper](https://papermc.io/)                            | ✅          | 1.20+   |
| [PurpurMC](https://purpurmc.org/)                       | ✅          | 1.20+   |
| [Spigot](https://www.spigotmc.org)                      | ✅          | 1.20+   |
| [Bukkit](https://bukkit.org)                            | ✅          | 1.20+   |
| [Folia](https://papermc.io/software/folia)              | ❌          |         |

<div id="installation"></div>

## Installation

1. **Download the mirlo jar file for your platform**. You can find the latest version on [our releases page](https://github.com/myth-MC/mirlo/releases).
2. **Add the mirlo jar file to your server's plugin folder**. Make sure to delete any older versions of mirlo.
3. **Fully restart your server**. Type `/stop` and start the server again [instead of using `/reload`](https://madelinemiller.dev/blog/problem-with-reload/).

Repeat the steps for every backend of your network.

<div id="usage"></div>

## Usage

Once you run mirlo for the first time it will generate a `config.yml` file under `/plugins/mirlo`. This file contains everything you might want to modify depending on your needs.

### Events

Events listen for specific actions that happen in the game. Right now you can use:
* **PLAYER_KILLS_PLAYER_EVENT** (player, targetPlayer): triggered whenever a player kills another player.
* **PLAYER_KILLS_CREATURE_EVENT** (player): triggered whenever a player kills a mob.
* **PLAYER_DEATH_EVENT** (targetPlayer): triggered whenever a player dies.

### Variables

Variables are temporary fields which can be altered when an event is triggered. Valid types are:
* **count**: a whole number. Can be positive or negative.
* **boolean**: true/false.

### Channels

Channels are the mean of communication where information will be exchanged with other backend servers or the proxy.

#### Placeholders

You can use placeholders when sending or receiving a plugin message from a specific channel. Valid placeholders are:
* Pre-defined variables.
* `player`
* `targetPlayer`

### Example _config.yml_ with hints

```yaml
variables:
  streak: # Variable "streak"
    type: count # Can be count or boolean
    scope: player # This variable will be unique for every player. Change to "global" if you want it to be the same for every player
    default: 0 # The default value whenever "streak" is resetted
    increase: # When should "streak" increase
      - PLAYER_KILLS_PLAYER_EVENT # A player kills another player
    decrease: [] # When should "streak" decrease. (never in this case)
    reset: # When should "streak" return to its default value
      - PLAYER_DEATH_EVENT # A player dies

channels:
  players: # Channel "mirlo:players"
    target: proxy # Where should messages from this channels be sent (survival, skywars, etc) (set to all if message should be sent to every backend)
    send: # What events should be sent
      - PLAYER_KILLS_PLAYER_EVENT,streak
      - PLAYER_DEATH_EVENT
    receive: [] # Won't do anything for now
```
