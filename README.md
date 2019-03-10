# Treebo Tickets
A Simple Tickets, Ideas & Review Plugin

## Permisions
```

permissions:
  tbtickets.admin.ontime:
    description:
    default: op
  tbtickets.admin.restart:
    description:
    default: false
  tbtickets.admin.updatechecker:
    description: 'Notify user if there is an update available'
    default: op
  tbtickets.builder:
    description: 'Lets builders see review tickets'
    default: false
  tbtickets.player.create:
    description: 'Allows a player to create a new ticket'
    default: true
  tbtickets.player.view.own:
    description: 'Allows a player to see their own tickets'
    default: true
  tbtickets.player.own:
    description: 'Allows a player to delete closed tickets.'
    default: true
  tbtickets.mod.close:
    description: 'Allows a player to delete closed tickets.'
    default: op 
  tbtickets.mod.view:
    description: 'Allows a player to see all tickets'
    default: op
  tbtickets.admin:
    description: 'Allows use of all tbTicketAdmin commands'
    default: false
  tbtickets.server.survival:
    description: 'Allows use of /survival'
    default: true
  tbtickets.server.hardcore:
    description: 'Allows use of /hardcore'
    default: true
  tbtickets.server.sky:
    description: 'Allows use of /skyblock, /caveblock, /acidisland, /acidislands and /skygrid'
    default: true
  tbtickets.server.creative:
    description: 'Allows use of /creative'
    default: true
  tbtickets.server.plots:
    description: 'Allows use of /plots'
    default: true
  tbtickets.server.prison:
    description: 'Allows use of /prison'
    default: true
  tbtickets.server.hub:
    description: 'Allows use of /lobby'
    default: true
  tbtickets.server.lobby:
    description: 'Allows use of /lobby'
    default: true
  tbtickets.server.games:
    description: 'Allows use of /games'
    default: true
  tbtickets.server.caveblock:
    description: 'Allows use of /caveblock'
    default: true
  
```


## Commands
```
  ### General:
  /tbTicket - Displays help for tbTicket command
  /tbTicket open -  Submits a ticket for the staff team to look into.
  /tbTicket close <ticket number> - Closes this ticket number if owned by the player
  /tbTicket view <ticket number> - Views details on this ticket number if owned by the player
  /tbTicket list - Lists all of this players tickets
  /idea - Allows a player to use the ticket system to submit ideas
  /discord - runs /discordsrv:discord link
  /discord default - runs /discordsrv:discord
  /discord <anything other than default> - runs /discordsrv:discord <anything other than default>

  ### Builder: 
  /review - Allows a player to request build review.
  /reviewclose - Closes a build review ticket.
  /reviewlist - Allows a player to use the ticket system to submit ideas.
  /reviewstats - Shows total review tickets.
  /reviewtp - Teleports to Builder tickets.
  /reviewupdate - Updates staff entries on a review ticket.
  /reviewview - Allows a player to use the ticket system to submit ideas
  
  ### Staff:
  /tbta - Help for tbta commands.
  /tbta list assigned  -  List all tickets assigned to this player if player has permission tbticket.view.any
  /tbta list unassigned - List all tickets not assigned to a player if player has permission tbticket.view.any
  /tbta list open - List all open tickets if player has permission tbticket.view.any
  /tbta list closed - list all closed tickets if player has permission tbticket.view.any
  /tbta close <ticket number> - Closes this ticket number if player has permission tbticket.close.any and is assigned to player
  /tbta view <ticket number> - Views details on this ticket number if player has permission tbticket.view.any
  /tbta claim <ticket number> - Assigns unassigned ticket to self
  /tbta unclaim <ticket number> - Assigns assigned ticket to unassigned if assigned to player
  /tbta tp - Teleports player to ticket location. If ticket location is on another server, it will attempt to transfer servers.
  /tbta update - Allows a player to update a tickets "staff steps" entry.
  
  ### Admin:
  /tbTicketAdmin - Displays help for tbTicketAdmin commands.
  /tbTicketAdmin reload - reloads the TreeboTickets config.
  /tbTicketAdmin list <assigned | unassigned | open | closed> - Lists all tickets that are in the selected category
  /tbTicketAdmin staffList <staff name> - Lists all tickets assigned to player <staff name>
  /tbTicketAdmin assign <ticket number> <staff name> - Assigns a ticket to a player with the name in the third command argument (<staff name>)
  /tbTicketAdmin close <ticket number> - No questions asked ticket close.             
  /tbTicketAdmin delete <ticket number> - Removes ticket from database, no questions asked.
  /tbTicketAdmin update <ticket number> <Message> - As with tbta this will add a comment to the ticket, but will ignore the assigned staff requirement              
  /tbTicketAdmin stats - Lists total tickets, assigned tickets, unassigned tickets, open tickets, closed tickets.                         

  ### Movement:
  /skyblock - Teleport you to Sky Server
  /skygrid - Teleport you to Sky Server
  /acisislands - Teleport you to Sky Server
  /prison - Teleport you to Prison Server
  /survial - Teleport you to Survival Server
  /hardcore - Teleport you to Hardcore Server
  /creative - Teleport you to Creative Server
  /plots - Teleport you to Creative Server
  /comp - Teleports you to the Creative Competition Plots world.
  /test- Teleport you to Test Server
  /lobby - Teleport you to Hub Server
  /games - Teleport you to Games Server

  ### Misc:
  /remoteexecute <command> <server> <args> - issues a command to another server (can only be used from console)
  /restarttimed - issues restart command in X ticks
  /onhere - Shows own ontime on the network
  /onall - Shows own ontime across all Treebo servers (disabled)
  /multiplecommands - Executes multiple commands in order from one command input. -- Eg. /multiplecommands /tp xXx_Pu55y_D357R0YA_xXx /gmsp /broadcast The All knowing eye casts it's gaze upon you xXx_Pu55y_D357R0YA_xXx

```

## Command Alias
```
 /tbticket - /ticket
```
