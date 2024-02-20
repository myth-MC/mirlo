<div align="center">
  <p>
    <h1>mirlo 🐦‍⬛</h1>
    <a href="https://github.com/myth-MC/mirlo/releases/latest"><img src="https://img.shields.io/github/v/release/myth-MC/mirlo?include_prereleases" alt="Latest release" /></a>
    <a href="https://github.com/myth-MC/mirlo/pulls"><img src="https://img.shields.io/github/issues-pr/myth-MC/mirlo" alt="Pull requests" /></a>
    <a href="https://github.com/myth-MC/mirlo/issues"><img src="https://img.shields.io/github/issues/myth-MC/mirlo" alt="Issues" /></a>
    <a href="https://github.com/myth-MC/mirlo/blob/main/LICENSE"><img src="https://img.shields.io/badge/license-GPL--3.0-blue.svg" alt="License" /></a>
    <br>
    A simple approach to <a href="https://web.archive.org/web/20220711204310/https://dinnerbone.com/blog/2012/01/13/minecraft-plugin-channels-messaging/">plugin messaging</a>
  </p>
</div>

<details open="open">
  <summary>Quick navigation</summary>
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
    <li>
      <a href="#api">API</a>
    </li>
    <li>
      <a href="#credits">Credits</a>
    </li>
  </ol>
</details>

<div id="information"></div>

## 📚 Information

**mirlo 🐦‍⬛** provides server owners with a simple way of implementing and handling plugin messaging between a proxy and its backends (check [Usage](#usage)).

>[!WARNING]
> mirlo is **not ready for production use** yet. The project is still missing an important part of its core functions (right now its sole purpose is sending plugin messages to a proxy). You can report any misbehaviours or share any feedback by [creating an issue](https://github.com/myth-MC/mirlo/issues). 

### Compatibility chart

|                                                         | Compatible? | Version |
|---------------------------------------------------------|-------------|---------|
| [Velocity](https://papermc.io/software/velocity)        | ✅          | 3.3.0   |
| [BungeeCord](https://www.spigotmc.org/wiki/bungeecord/) | ❌          |         |
| [Paper](https://papermc.io/)                            | ✅          | 1.13+   |
| [PurpurMC](https://purpurmc.org/)                       | ✅          | 1.13+   |
| [Spigot](https://www.spigotmc.org)                      | ✅          | 1.13+   |
| [Bukkit](https://bukkit.org)                            | ✅          | 1.13+   |
| [Folia](https://papermc.io/software/folia)              | ❌          |         |
| [Minestom](https://minestom.net)                        | ❌          |         |

<div id="installation"></div>

## 📥 Installation

1. **Download the mirlo jar file for your platform**. You can find the latest version on [our releases page](https://github.com/myth-MC/mirlo/releases).
2. **Add the mirlo jar file to your server's plugin folder**. Make sure to delete any older versions of mirlo.
3. **Fully restart your server**. Type `/stop` and start the server again [instead of using `/reload`](https://madelinemiller.dev/blog/problem-with-reload/).

Repeat the steps for every backend of your network.

<div id="usage"></div>

## 🖊️ Usage

When you run mirlo for the very first time it will automatically generate three different configurable files that can be adjusted depending on your server's needs:
* `settings.yml` contains general settings
* `channels.yml` contains every channel to listen for
* `variables.yml` contains every variable

Every file has one or more examples with hints.

### Events

Events listen for specific actions that happen in the game. Right now you can use:
* **PLAYER_KILLS_PLAYER_EVENT** (player, targetPlayer): triggered whenever a player kills another player.
* **PLAYER_KILLS_CREATURE_EVENT** (player): triggered whenever a player kills a mob.
* **PLAYER_DEATH_EVENT** (targetPlayer): triggered whenever a player dies.

_Values in parentheses are placeholders._

### Variables

Variables are temporary fields which can be altered when an event is triggered. Valid types are:
* **count**: a whole number. Can be positive or negative.
* **boolean**: true/false.

### Channels

Channels are the mean of communication where information will be exchanged with other backend servers or the proxy.

#### Placeholders

You can use placeholders when sending or receiving a plugin message from a specific channel. Valid placeholders are:
* Pre-defined variables (variables.yml)
* `player`
* `targetPlayer`

<div id="api"></div>

## ⚙️ API
**mirlo** exposes a simple API via the `Mirlo.get()` method.

You can check [our guide on using mirlo-api](https://github.com/myth-MC/mirlo/blob/0.3.0-dev/api/README.md/).

<div id="credits"></div>

## 📜 Credits
* Events API and config is taken from [Sonar](https://github.com/jonesdevelopment/sonar/tree/main).
