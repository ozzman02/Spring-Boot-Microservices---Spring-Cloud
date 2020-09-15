package guru.sfg.brewery.beer_service.web.mappers;

import guru.sfg.brewery.beer_service.domain.Beer;
import guru.sfg.brewery.beer_service.domain.Beer.BeerBuilder;
import guru.sfg.brewery.model.BeerDto;
import guru.sfg.brewery.model.BeerDto.BeerDtoBuilder;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-09-13T19:03:33-0600",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 11.0.2 (Oracle Corporation)"
)
@Component
@Qualifier("delegate")
public class BeerMapperImpl_ implements BeerMapper {

    @Autowired
    private DateMapper dateMapper;

    @Override
    public BeerDto beerToBeerDto(Beer beer) {
        if ( beer == null ) {
            return null;
        }

        BeerDtoBuilder beerDto = BeerDto.builder();

        beerDto.id( beer.getId() );
        if ( beer.getVersion() != null ) {
            beerDto.version( beer.getVersion().intValue() );
        }
        beerDto.createdDate( dateMapper.asOffsetDateTime( beer.getCreatedDate() ) );
        beerDto.lastModifiedDate( dateMapper.asOffsetDateTime( beer.getLastModifiedDate() ) );
        beerDto.beerName( beer.getBeerName() );
        beerDto.beerStyle( beer.getBeerStyle() );
        beerDto.upc( beer.getUpc() );
        beerDto.quantityOnHand( beer.getQuantityOnHand() );
        beerDto.price( beer.getPrice() );

        return beerDto.build();
    }

    @Override
    public BeerDto beerToBeerDtoWithInventory(Beer beer) {
        if ( beer == null ) {
            return null;
        }

        BeerDtoBuilder beerDto = BeerDto.builder();

        beerDto.id( beer.getId() );
        if ( beer.getVersion() != null ) {
            beerDto.version( beer.getVersion().intValue() );
        }
        beerDto.createdDate( dateMapper.asOffsetDateTime( beer.getCreatedDate() ) );
        beerDto.lastModifiedDate( dateMapper.asOffsetDateTime( beer.getLastModifiedDate() ) );
        beerDto.beerName( beer.getBeerName() );
        beerDto.beerStyle( beer.getBeerStyle() );
        beerDto.upc( beer.getUpc() );
        beerDto.quantityOnHand( beer.getQuantityOnHand() );
        beerDto.price( beer.getPrice() );

        return beerDto.build();
    }

    @Override
    public Beer beerDtoToBeer(BeerDto beerDto) {
        if ( beerDto == null ) {
            return null;
        }

        BeerBuilder beer = Beer.builder();

        beer.id( beerDto.getId() );
        if ( beerDto.getVersion() != null ) {
            beer.version( beerDto.getVersion().longValue() );
        }
        beer.createdDate( dateMapper.asTimestamp( beerDto.getCreatedDate() ) );
        beer.lastModifiedDate( dateMapper.asTimestamp( beerDto.getLastModifiedDate() ) );
        beer.beerName( beerDto.getBeerName() );
        beer.beerStyle( beerDto.getBeerStyle() );
        beer.upc( beerDto.getUpc() );
        beer.price( beerDto.getPrice() );
        beer.quantityOnHand( beerDto.getQuantityOnHand() );

        return beer.build();
    }
}
