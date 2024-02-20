<div align="center">
  <p>
    <h1>mirlo 🐦‍⬛</h1>
    A simple approach to <a href="https://web.archive.org/web/20220711204310/https://dinnerbone.com/blog/2012/01/13/minecraft-plugin-channels-messaging/">plugin messaging</a>
  </p>
</div>

## How to use the API
Before starting to actually use the API, you will have to add the [JitPack](https://jitpack.io/) repository and the dependency to your project.

```xml
	<repositories>
		<repository>
		    	<id>jitpack.io</id>
		    	<url>https://jitpack.io</url>
			</repository>
	</repositories>

  ...
	<dependencies>
		<dependency>
			<groupId>com.github.myth-MC.mirlo</groupId>
		    	<artifactId>mirlo-api</artifactId>
		    	<version>0.3.0</version>
		</dependency>
	</dependencies>
```

You can access all the main API functions by using `Mirlo.get()`.

## Listening to events
**mirlo-api** provides an easy way to integrate events across different platforms by using its built-in event system. Let's start by creating a `MirloEventListener`:

```java
import me.u8092.mirlo.api.event.MirloEvent;
import me.u8092.mirlo.api.event.MirloEventListener;

public final class DemoListener implements MirloEventListener {

  @Override
  public void handle(final MirloEvent event) {
    // This code will run every time a MirloEvent is triggered
  }
}
```

We can then check for the event we want to listen for:

```java
import me.u8092.mirlo.api.event.MirloEvent;
import me.u8092.mirlo.api.event.MirloEventListener;
import me.u8092.mirlo.api.event.impl.MirloMessageSentEvent;

public final class DemoListener implements MirloEventListener {

  @Override
  public void handle(final MirloEvent event) {
    // We want to check for MirloMessageSentEvent
    if(event instanceof MirloMessageSentEvent messageSentEvent) {
      // This code will run every time a MirloMessageSentEvent is triggered
      System.out.println("Sent a message through channel " + messageSentEvent.getChannel()
          + " to " + messageSentEvent.getTarget()
          + ": " + messageSentEvent.getMessage());
    }
  }
}
```

Last but not least, we will register our event listener using the mirlo API:
```java
Mirlo.get().getEventManager().registerListener(new DemoListener());
```

## Sending a message

You can send a message by calling `MirloMessage.send()`:

```java
import me.u8092.mirlo.api.MirloMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        MirloMessage message = new MirloMessage(
                "test", // Channel
                "test", // Target
                event.getPlayer().getName() + " has joined"); // Message
        
        message.send();
    }
}

```