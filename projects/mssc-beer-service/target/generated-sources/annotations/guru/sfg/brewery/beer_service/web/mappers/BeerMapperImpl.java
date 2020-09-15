package guru.sfg.brewery.beer_service.web.mappers;

import guru.sfg.brewery.beer_service.domain.Beer;
import guru.sfg.brewery.model.BeerDto;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-09-13T19:03:33-0600",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
@Component
@Primary
public class BeerMapperImpl extends BeerMapperDecorator implements BeerMapper {

    @Autowired
    @Qualifier("delegate")
    private BeerMapper delegate;

    @Override
    public BeerDto beerToBeerDto(Beer beer)  {
        return delegate.beerToBeerDto( beer );
    }

    @Override
    public Beer beerDtoToBeer(BeerDto beerDto)  {
        return delegate.beerDtoToBeer( beerDto );
    }
}
