import React from 'react';
import { head } from 'ramda';
import { TextField, Button, Typography, InputLabel, FormControl, Select } from '@material-ui/core';
import { WrappedForm } from './style';
import { Formik, FormikProps } from 'formik';
import { getTypes } from '../../actions/content-async';
import { useAsync } from 'react-use';
import { SendMusicFormSchema } from '../../validation-schemas';
import { HOME_PATH } from '../../router';
import { useHistory } from 'react-router-dom';
import { IDType } from '../../reducers/reducers';

interface IUploadMusic {
  uploadMusic: Function;
}

export interface IFormUploadMusic {
  author: string;
  title: string;
  type: string;
  album: string;
}

const InitialValues: IFormUploadMusic = {
  author: '',
  title: '',
  type: '',
  album: '',
};

const UploadMusic = ({ uploadMusic }: IUploadMusic) => {
  const picture = React.useRef(null);
  const music = React.useRef(null);
  const [types, setTypes] = React.useState([]);
  const [musicInput, setMusic] = React.useState();
  const [cover, setCover] = React.useState();
  const [genres, setGenres] = React.useState([]);
  const { push } = useHistory();
  useAsync(async () => {
    const data: any = await getTypes();
    setTypes(data);
  }, []);

  const handleSubmit = async (values: IFormUploadMusic): Promise<void> => {
    debugger;
    await uploadMusic({
      ...values,
      genres: genres.map((data) => data.genre),
      track: head(music.current.files),
      cover: head(picture.current.files),
    });
    push(HOME_PATH);
  };

  const handleOnAddGenge = () => {
    setGenres((genres) => [
      ...genres,
      {
        id: genres.length,
        genre: '',
      },
    ]);
  };

  const handleDeleteGenre = React.useCallback(
    (index: IDType) => () => {
      setGenres((genres) => genres.filter(({ id }) => id !== index));
    },
    []
  );

  const handleInputChange = React.useCallback(
    (id) => ({ target: { value } }: any) => {
      setGenres((original) =>
        original.reduce((acc, item) => {
          if (item.id === id) item.genre = value;
          return [...acc, item];
        }, [])
      );
    },
    []
  );

  return (
    <Formik initialValues={InitialValues} onSubmit={handleSubmit} validationSchema={SendMusicFormSchema}>
      {({ errors, values, handleChange, isSubmitting }: FormikProps<IFormUploadMusic>) => (
        <WrappedForm>
          <Typography variant="h1">Upload music</Typography>
          <TextField
            value={values.author}
            fullWidth
            name="author"
            label="Author"
            onChange={handleChange}
            variant="outlined"
            placeholder="Enter author"
            id="author"
            data-testid="input-author"
          />
          {errors.author && <div className="error-field">{errors.author}</div>}
          <TextField
            value={values.title}
            fullWidth
            name="title"
            id="title"
            label="Title"
            onChange={handleChange}
            variant="outlined"
            placeholder="Enter title"
            data-testid="input-title"
          />
          {errors.title && <div className="error-field">{errors.title}</div>}
          <TextField
            value={values.album}
            fullWidth
            name="album"
            label="Album"
            onChange={handleChange}
            variant="outlined"
            placeholder="Enter album"
            data-testid="input-album"
          />
          {errors.album && <div className="error-field">{errors.album}</div>}
          <FormControl variant="filled" data-testid="type-id">
            <InputLabel htmlFor="filled-age-native-simple">Type</InputLabel>
            <Select
              native
              value={values.type}
              onChange={handleChange}
              inputProps={{
                name: 'type',
                id: 'type-music',
              }}
            >
              <option aria-label="None" value="">
                Choose your type
              </option>
              {types.map((type) => {
                return (
                  <option key={type.id} value={type.id}>
                    {type.title}
                  </option>
                );
              })}
            </Select>
            {errors.type && <div className="error-field">{errors.type}</div>}
          </FormControl>
          <Button className="button" onClick={handleOnAddGenge} data-testid="genre-test">
            Add genre
          </Button>
          {!genres.length && <div className="error-field">Required</div>}
          {genres.map(({ id, genre }) => {
            return (
              <div data-testid="genre-testas" key={id.toString()}>
                <TextField
                  name="genre"
                  value={genre}
                  label="Genre"
                  onChange={handleInputChange(id)}
                  variant="outlined"
                  placeholder="Enter genre"
                />
                {!genre && <div className="error-field">Required</div>}
                <Button className="button" onClick={handleDeleteGenre(id)} data-testid="genre-delete">
                  Delete genre
                </Button>
              </div>
            );
          })}
          <div>
            <input
              accept="image/*"
              className="input"
              id="contained-button-file"
              multiple
              type="file"
              ref={picture}
              onChange={(e: any) => {
                setCover(e.target.value);
              }}
            />
            <label className="picture" htmlFor="contained-button-file">
              <Button className="upload" component="span">
                Upload Cover
              </Button>
            </label>
            <div className="status-input">{cover ? cover.replace(/^.*[\\]/, '') : 'Файл не выбран'}</div>
          </div>
          <div>
            <input
              accept="audio/*"
              className="input"
              id="contained-button-filed"
              multiple
              type="file"
              ref={music}
              onChange={(e: any) => {
                setMusic(e.target.value);
              }}
            />
            <label htmlFor="contained-button-filed">
              <Button className="upload" component="span" data-testid="music-btn">
                Upload Audio
              </Button>
            </label>
            <div className="status-input">{musicInput ? musicInput.replace(/^.*[\\]/, '') : 'Файл не выбран'}</div>
          </div>
          <Button
            data-testid="submit-btn"
            id="submit"
            fullWidth
            type="submit"
            variant="outlined"
            color="primary"
            className="send"
            disabled={isSubmitting}
          >
            Send
          </Button>
        </WrappedForm>
      )}
    </Formik>
  );
};
export default UploadMusic;
