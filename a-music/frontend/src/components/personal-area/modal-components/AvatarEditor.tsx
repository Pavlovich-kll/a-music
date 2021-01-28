import React from 'react';
import AddAPhotoIcon from '@material-ui/icons/AddAPhoto';
import { Formik, Form } from 'formik';
import Button from '@material-ui/core/Button';
import { toast } from 'react-toastify';
import HTTP from '../../../common/api';

const AVATAR_UPLOAD_ENDPOINT: string = '/user-service/images';
const USER_ID_LOCALSTORAGE: string = 'USER_ID_KEY';
const id = localStorage.getItem(USER_ID_LOCALSTORAGE);
const USER_UPDATE_ENDPOINT: string = `/user-service/users/update/${id}`;
const objToString = (obj: any): string => obj.fileName;

interface IFormikValues {
  avatar: any;
}

const InitialValues: IFormikValues = {
  avatar: null,
};
interface IOnClose {
  onClose: () => any;
}

const AvatarEditor = ({ onClose }: IOnClose) => {
  const [avatar, setAvatar] = React.useState();

  const handleSubmit = async () => {
    if (avatar) {
      const formdata = new FormData();
      formdata.append('file', avatar.content, 'image.jpg');
      const uploadRequest = await HTTP.post(AVATAR_UPLOAD_ENDPOINT, formdata); //ответ верный, fileName: искомое имя
      const userUpdateRequest = await HTTP.patch(USER_UPDATE_ENDPOINT, { avatar: objToString(uploadRequest) });
    }
    if (!avatar) {
      toast.error('You forgot to upload an avatar');
    } else {
      onClose();
    }
  };
  const handleChange = (event: any) => {
    const reader = new FileReader();
    const [content] = event.target.files;
    reader.onloadend = () => {
      setAvatar({ file: reader.result, content });
    };
    reader.readAsDataURL(content);
  };

  return (
    <div className="changeAvatarBlock">
      <Formik initialValues={InitialValues} onSubmit={handleSubmit}>
        <Form>
          <div className="ChangeAvatarBlock">
            <label className="AvatarLabel">
              {avatar ? (
                <img src={avatar.file} />
              ) : (
                <>
                  <AddAPhotoIcon />
                  <span className="title">Add file</span>
                </>
              )}
              <input
                name="file"
                accept="image/*"
                className="input"
                id="contained-button-avatar"
                multiple
                type="file"
                onChange={handleChange}
              />
            </label>
            <div className="status-input"></div>
            <Button className="uploadAvatar" type="submit" variant="contained" color="primary">
              Upload Avatar
            </Button>
          </div>
        </Form>
      </Formik>
    </div>
  );
};

export default AvatarEditor;
