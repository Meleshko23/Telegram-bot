package pro.sky.telegrambot.component;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.model.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class SchedulesComponent {

    private final TelegramBot telegramBot;
    private final NotificationTaskRepository notificationTaskRepository;

    public SchedulesComponent(TelegramBot telegramBot, NotificationTaskRepository notificationTaskRepository) {
        this.telegramBot = telegramBot;
        this.notificationTaskRepository = notificationTaskRepository;
    }

//    the method runs every minute and checks if there are records with the correct date and time.
//    If yes, then oct notification

    @Scheduled(cron = "0 0/1 * * * *")
    public void getListNotificator() {
        LocalDateTime ldt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);

        List<NotificationTask> listNotify = notificationTaskRepository.findAllByDateTime(ldt);
        listNotify.stream()
                .forEach(notify -> {
                    Long chatId = notify.getChartId(); // get id chart
                    SendMessage sendMess = new SendMessage(chatId, notify.getMessage()); // get message for sending
                    SendResponse response = telegramBot.execute(sendMess); // send message
                });
    }
}
