package be.ordina.jworks.gaming.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.view.RedirectView;

@Component
public class CustomConnectController extends ConnectController {

    @Autowired
    public CustomConnectController(ConnectionFactoryLocator connectionFactoryLocator, ConnectionRepository connectionRepository) {
        super(connectionFactoryLocator, connectionRepository);
    }

    @Override
    protected RedirectView connectionStatusRedirect(String providerId, NativeWebRequest request) {
        return new RedirectView("/");
    }
}
