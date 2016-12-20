# -PluginRequest-ClockTower
A simplified but functional version of the plugin requested in: https://bukkit.org/threads/ok-so-this-might-be-a-little-difficult.438834/#post-3457682

I would like to ask anyone who has experience with coding things similar to the vanilla minecraft @a, @p, @e, @r, etc operators to please offer assistance in making this work fulling. I will consider pull requests and other forms of input as I feel learning how to do this will help OP as well an provide me with more experience as a developer.

Prenote: This plugin is coded very simply and, as such, may feel lacking in some areas or may not function exactly as intended. As example of this is that timers will be removed with stopped instead of waiting at 00:00

	Commands:
	/clocktower	#Main command--all commands stem off of this
	/ct 			#can be used in place of /clocktower
	
	Arguments:
		start [seconds] [players]	# Starts a timer for specified player
										# If the player already has a timer, their time will be set
		
		pause (-u) [players] 			# Pauses the timer for listed players
										# Adding -u will unpause the timers
		
		stop [players]					# Stops target player's timer (Stopping a timer removes it from the timer list)
										# If a timer is removed from the list, it no longer exists
		
		stopall							# Stops all currently running timers
		
		add [seconds] [players]		# Adds a specified amount of time to the timer of all listed players
										# Players listed who do not have one active will have a timer started
										# Time may be prefixed with - to subtract, remaining time cannot go below 00:00
		
		set [seconds] [players]		# Sets the timer for listed players to specified duration
										# If the player does not have an active timer, a timer will be started for them
										
		get [receiver] [players]		# Sends the remaining times from all listed players to [receiver]

	Allowed @ Terms:
		#Note, using a capital letter (e.g. @G) uses a global scope, and will include players without active timers
		@t								# Targets a random player with an active timer
		@g								# Targets all players with an active timer
		
		Thanks to the hard work of ZombieStriker and his CommandUtils resource, this plugin can use all of the vanilla @ annotations as well at the plugin-specific @t and @g that I coded.