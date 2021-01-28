import React, { useState, useCallback } from 'react';
import Checkbox from '@material-ui/core/Checkbox';
import { NotificationsWrapper } from './styles';
import { useDispatch, useSelector } from 'react-redux';
import { IStore } from '../../../../store';
import { updateNotifications } from '../../../../actions/user';

const blocks = [
  {
    id: 1,
    title: 'Музыка',
    subtitle: '',
  },
  {
    id: 2,
    title: 'Подкасты',
    subtitle: 'Подкасты доступные только в некоторых странах',
  },
  {
    id: 3,
    title: 'Наши проекты',
    subtitle: '',
  },
];

const Notifications = () => {
  const notificationsContent: any = useSelector(({ user }: IStore) => user.notifications);
  const notificationBody: any = useSelector(({ user }: IStore) => user.notificationBody);
  const userId: any = useSelector(({ user }: IStore) => user.current.id);
  const dispatch = useDispatch();
  const [values, setValues] = useState(notificationsContent);

  const handleChange = useCallback(
    async (e: any) => {
      setValues(
        values.map((item: any) => {
          if (item.id === Number(e.target.value)) {
            item[`${e.target.name}`] = !item[`${e.target.name}`];
          }
          return item;
        })
      );
      await dispatch(updateNotifications(userId, values[e.target.value - 1]));
    },
    [values, userId]
  );

  return (
    <NotificationsWrapper>
      {blocks.map(({ id, title, subtitle }) => {
        return (
          <div className="block" key={id}>
            <div className="containers">
              <div className="left-container">
                <h3 className="block-title">{title}</h3>
                <p className="chapter-text">{subtitle}</p>
              </div>
              <div className="right-container">
                <div className="checkbox-header">Мобильные push-уведомления</div>
                <div className="checkbox-header">Электронные письма</div>
                <div className="checkbox-header">Текстовые сообщения</div>
              </div>
            </div>
            <div>
              {notificationBody
                .filter((item: any) => {
                  if (id === 1) return item.id < 6;
                  if (id === 2) return item.id === 6;
                  if (id === 3) return item.id > 6;
                })
                .map(({ id, description, secondaryDescription }: any) => {
                  return (
                    <div key={id} className="chapter">
                      <div className="left-container">
                        <h4 className="chapter-title">{description}</h4>
                        <p className="chapter-text">{secondaryDescription}</p>
                      </div>
                      <div className="right-container">
                        <Checkbox
                          className="checkbox"
                          value={id}
                          name="mobileNotification"
                          checked={values[id - 1].mobileNotification}
                          onChange={handleChange}
                        />
                        <Checkbox
                          className="checkbox"
                          value={id}
                          name="mailNotification"
                          checked={values[id - 1].mailNotification}
                          onChange={handleChange}
                        />
                        <Checkbox
                          className="checkbox"
                          value={id}
                          name="textNotification"
                          checked={values[id - 1].textNotification}
                          onChange={handleChange}
                        />
                      </div>
                    </div>
                  );
                })}
            </div>
          </div>
        );
      })}
    </NotificationsWrapper>
  );
};

export default Notifications;
