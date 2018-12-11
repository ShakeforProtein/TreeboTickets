# Treebo Tickets
A Simple Tickets Plugin

## Permisions
```

### Defaults to True
  tbtickets.create - Allows a player to create a new ticket.
           .view.own - Allows a player to view their own tickets.
           .close.own - Allows a player to close their own tickets.
           .survival - Allows use of /survival command (teleport to server with name survival)  ** IMPLEMENTATION INCOMPLETE
           .hardcore - Allows use of /survival command (teleport to server with name hardcore)  **
           .creative - Allows use of /survival command (teleport to server with name creative)  **
           .acidislands - Allows use of /survival command (teleport to server with name sky)  ** 
           .skyblock - Allows use of /survival command (teleport to server with name sky)  **
           .plots - Allows use of /survival command (teleport to server with name creative)  **
           .prison - Allows use of /survival command (teleport to server with name prison)  **
           .test - Allows use of /survival command (teleport to server with name test)
  
### Defaults to False
  tbtickets.delete.closed - Allows a player to delete closed tickets.
           
### Defaults to OP
  tbtickets.updatechecker - Notifies player when an update is available.
           .close.any - Allows a player to close any ticket.
           .delete.any - Allows a player to remove tickets from the database
           .view.any - Allows a player to view any ticket
           .admin - Allows use of all admin commands
  
```


## Commands
```
  /tbTicket - Displays help for tbTicket command
           open -  Submits a ticket for the staff team to look into.
           close <ticket number> - Closes this ticket number if owned by the player
           view <ticket number> - Views details on this ticket number if owned by the player
           list - Lists all of this players tickets
           
  /tbta - Help for tbta commands.
        list assigned  -  List all tickets assigned to this player if player has permission tbticket.view.any
        list unassigned - List all tickets not assigned to a player if player has permission tbticket.view.any
        list open - List all open tickets if player has permission tbticket.view.any
        list closed - list all closed tickets if player has permission tbticket.view.any
        close <ticket number> - Closes this ticket number if player has permission tbticket.close.any and is assigned to player
        view <ticket number> - Views details on this ticket number if player has permission tbticket.view.any
        claim <ticket number> - Assigns unassigned ticket to self
        unclaim <ticket number> - Assigns assigned ticket to unassigned if assigned to player
        tp - Teleports player to ticket location. If ticket location is on another server, it will attempt to transfer servers.
        update - Allows a player to update a tickets "staff steps" entry.
  
  
  /tbTicketAdmin - Displays help for tbTicketAdmin commands.
                 delete <ticket number> - Removes ticket from database, no questions asked.
                 close <ticket number> - No questions asked ticket close.
                 assign <ticket number> <staff name> - Assigns a ticket to a player with the name in the third command argument (<staff name>)
                 list <assigned | unassigned | open | closed> - Lists all tickets that are in the selected category
                 staffList <staff name> - Lists all tickets assigned to player <staff name>
                 update <ticket number> <Message> - As with tbta this will add a comment to the ticket, but will ignore the assigned staff requirement
                 reload - reloads the TreeboTickets config.
                 stats - Lists total tickets, assigned tickets, unassigned tickets, open tickets, closed tickets.
  
  
```

## Command Alias
```
 
```
