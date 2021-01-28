import React, { useEffect } from 'react';
import { ButtonBack, ButtonNext, CarouselProvider, Slide, Slider } from 'pure-react-carousel';
import 'pure-react-carousel/dist/react-carousel.es.css';
import { GalleryStyle } from './style';
import KeyboardArrowLeftIcon from '@material-ui/icons/KeyboardArrowLeft';
import KeyboardArrowRightIcon from '@material-ui/icons/KeyboardArrowRight';
import { useSelector } from 'react-redux';
import { NavLink } from 'react-router-dom';
import { IStore } from '../../store';
import { IPlayList } from '../../reducers/reducers';

const Gallery = () => {
  const contentPlayLists: IPlayList[] = useSelector(({ content }: IStore) => content.playLists);

  return (
    <GalleryStyle data-testid="some">
      <CarouselProvider
        visibleSlides={4}
        totalSlides={contentPlayLists.length}
        naturalSlideWidth={10}
        naturalSlideHeight={10}
        dragEnabled={false}
      >
        <h1 className="title__carousel">Popular playlist</h1>
        <Slider>
          {contentPlayLists.map((data: any) => {
            return (
              <NavLink to={`/collection/${data._id}`} key={data._id}>
                <Slide data-testid="slide" index={data._id}>
                  <div data-testid="main__div" id={data._id} className="slide">
                    <img
                      data-testid="img"
                      className="image"
                      src={`${process.env.API_URL}music-service/content/file/${data.pic}`}
                      alt="pictures"
                    />
                    <span data-testid="title" className="title__group">
                      {data.title}
                    </span>
                    <span>
                      <span data-testid="track_count">{data.tracks.length}</span> tracks -{' '}
                      <span data-testid="likes">{data.likes}</span> likes
                    </span>
                  </div>
                </Slide>
              </NavLink>
            );
          })}
        </Slider>
        <ButtonBack>
          <KeyboardArrowLeftIcon />
        </ButtonBack>
        <ButtonNext>
          <KeyboardArrowRightIcon />
        </ButtonNext>
      </CarouselProvider>
    </GalleryStyle>
  );
};

export default Gallery;
