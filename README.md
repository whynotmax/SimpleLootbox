# SimpleLootbox - A plugin every minehut server needs!

SimpleLootbox is a plugin that allows you to create custom lootboxes with custom rewards. This plugin is perfect for any
server that wants to add a little extra fun for their players. You can create as many lootboxes as you want and
customize them to your liking. You can also create custom rewards for each lootbox, so you can give your players the
chance to win some awesome prizes!

## Features

- Create custom lootboxes with custom rewards
- Customize the chance of getting each reward
- Customize rarities for each reward
- Live preview of lootboxes in-game
- Live drops (see "What are live drops?" below)

## Commands

- `/lootbox create <name>` - Create a new lootbox
- `/lootbox delete <name>` - Delete a lootbox
- `/lootbox list` - List all lootboxes
- `/lootbox give <player> <lootbox> <amount>` - Give a player a lootbox
- `/lootbox give all <lootbox> <amount>` - Give all online players a lootbox
- `/lootbox edit <lootbox>` - Edit a lootbox
- `/lootbox preview <lootbox>` - Places a block display of the lootbox at the player's location (*)

(*) Needs additional testing before release

## What are live drops?

Live drops are a feature that allows you to see what other players are getting from their lootboxes in real-time. This
feature is perfect for streamers or YouTubers who want to show off the lootboxes on their server. You can enable or
disable live drops in the `config.yml` file.

## Permissions

- `simplelootbox.admin` - Allows access to all commands

## Support

If you need help with the plugin or have any questions, feel free to join
the [Discord server](https://discord.mzcy.dev/) of [whynotmax](https://github.com/whynotmax)! We're always happy to
help. Also, feel free to open an issue on the [GitHub repository](https://github.com/whynotmax/SimpleLootbox) if you
encounter any bugs or have any feature requests.

## Installation

To install the plugin, simply download the latest version from
the [releases page](https://github.com/whynotmax/SimpleLootbox/releases) and upload it to your server's `plugins`
folder. Make sure to restart your server after installing the plugin.

## Building from source

If you want to build the plugin from source, you can do so by cloning the repository and running the following command:

```shell
./gradlew clean shadowJar
```

The plugin JAR file will be located in the `plugin/build/libs` folder.

## Developer API

SimpleLootbox has a developer API that allows you to create custom lootboxes and rewards programmatically. You can find
the API documentation [here](https://github.com/whynotmax/SimpleLootbox/wiki/Developer-API).

## Contributing

If you want to contribute to the plugin, feel free to open a pull request on
the [GitHub repository](https://github.com/whynotmax/SimpleLootbox). We're always looking for new ideas and
improvements! Make sure to read the [CONTRIBUTING.md](.github/CONTRIBUTING.md) file before contributing.

## License

This project is licensed under the MIT License - see the [LICENSE](.github/LICENSE.md) file for details.