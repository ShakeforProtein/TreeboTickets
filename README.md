# Treebo Tickets
A Simple Tickets, Ideas & Review Plugin

## Permisions
```

### Defaults to True
  tbtickets.create - Allows a player to create a new ticket.
  tbtickets.view.own - Allows a player to view their own tickets.
  tbtickets.close.own - Allows a player to close their own tickets.
  tbtickets.server.survival - Allows use of /survival command (teleport to server with name survival) (or to the survival world if on correct server)
  tbtickets.server.hardcore - Allows use of /hardcore command (teleport to server with name hardcore) (or to the hardcore world if on correct server)
  tbtickets.server.creative - Allows use of /creative command (teleport to server with name creative) (or to the creative world if on correct server)
  tbtickets.server.acidislands - Allows use of /acidislands command (teleport to server with name sky) (or to the acid islands world if on correct server)
  tbtickets.server.skyblock - Allows use of /skyblock command (teleport to server with name sky) (or to the skyblock world if on correct server)
  tbtickets.server.skygrid - Allows use of /skygrid command (teleport to server with name sky) (or to the skygrid world if on correct server)
  tbtickets.server.plots - Allows use of /plots command (teleport to server with name creative)
  tbtickets.server.prison - Allows use of /prison command (teleport to server with name prison) (or to the prison world if on correct server)
  tbtickets.server.test - Allows use of /test command (teleport to server with name test) (or to the test / test end world if on correct server)
  tbtickets.server.lobby - Allows use of /lobby command (teleport to server with name lobby) (or to the hub world if on correct server)
  tbtickets.server.games - Allows use of /games command (teleport to server with name games) (or to the games world if on correct server)

### Defaults to False
  tbtickets.delete.closed - Allows a player to delete closed tickets.
  tbtickets.builder - Lets builders see review tickets
  tbtickets.restart - Allows use of /restarttimed to restart the server in a specified amount of ticks plus 60
  tbtickets.remoteexecute - Allows use of the remoteexecute command. (This doesn't work in game and can only be used from a shared console)(Essentially useless)


### Defaults to OP
  tbtickets.updatechecker - Notifies player when an update is available.
  tbtickets.close.any - Allows a player to close any ticket.
  tbtickets.delete.closed - Allows a player to delete closed tickets.
  tbtickets.delete.any - Allows a player to remove tickets from the database.
  tbtickets.view.any - Allows a player to view any ticket.
  tbtickets.admin - Allows use of all admin commands.
  tbtickets.restore - Allows a player to restore a deleted ticket.
  tbtickets.ontime - Allows player to use /onhere.
  
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