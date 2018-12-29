# Treebo Tickets
A Simple Tickets, Ideas & Review Plugin

## Permisions
```

### Defaults to True
  tbtickets.create - Allows a player to create a new ticket.
  tbtickets.view.own - Allows a player to view their own tickets.
  tbtickets.close.own - Allows a player to close their own tickets.
  tbtickets.survival - Allows use of /survival command (teleport to server with name survival)
  tbtickets.hardcore - Allows use of /hardcore command (teleport to server with name hardcore)
  tbtickets.creative - Allows use of /creative command (teleport to server with name creative)
  tbtickets.acidislands - Allows use of /acidislands command (teleport to server with name sky)
  tbtickets.skyblock - Allows use of /skyblock command (teleport to server with name sky)
  tbtickets.plots - Allows use of /plots command (teleport to server with name creative)
  tbtickets.prison - Allows use of /prison command (teleport to server with name prison)
  tbtickets.test - Allows use of /test command (teleport to server with name test)
  tbtickets.server.lobby - Allows use of /lobby command (teleport to server with name lobby)
  
### Defaults to False
  tbtickets.delete.closed - Allows a player to delete closed tickets.
  tbtickets.builder - Lets builders see review tickets
  tbtickets.restart -
  tbtickets.remoteexecute -
  
### Defaults to OP
  tbtickets.updatechecker - Notifies player when an update is available.
  tbtickets.close.any - Allows a player to close any ticket.
  tbtickets.delete.closed - Allows a player to delete closed tickets.
  tbtickets.delete.any - Allows a player to remove tickets from the database.
  tbtickets.view.any - Allows a player to view any ticket.
  tbtickets.admin - Allows use of all admin commands.
  tbtickets.restore - Allows a player to restore a deleted ticket.
  tbtickets.ontime - 
  
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
  /skyblock - Teleport you to <server> 
  /acisislands - Teleport you to <server> 
  /skygrid - Teleport you to <server> (To be added) 
  /prison - Teleport you to <server> 
  /survial - Teleport you to <server> 
  /hardcore - Teleport you to <server> 
  /creative - Teleport you to <server> 
  /plots - Teleport you to <server> 
  /test- Teleport you to <server> 
  /lobby - Teleport you to <server> 
  /games - Teleport you to <server>
  
  ### Misc:
  /remoteexecute <command> <server> <args> - issues a command to another server (can only be used from console)
  /restarttimed - issues restart command in X ticks
  /onhere - Shows own ontime on this server
  /onall - Shows own ontime across all Treebo servers
  
```

## Command Alias
```
 /tbticket - /ticket
```
