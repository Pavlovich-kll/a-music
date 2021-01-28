import { KafkaClient, HighLevelProducer, Consumer } from 'kafka-node';
import { KAFKA_URL } from '../constants/kafka';
import { config } from 'dotenv';
import { MongooseDocument } from 'mongoose';
import ModelFactory from '../models';

config();

export class BackgroundService {
    private readonly audioModel: any;

    constructor() {
        const modelFactoryInstance: ModelFactory = new ModelFactory();
        this.audioModel = modelFactoryInstance.getModel('AudioModel');
        this.init_kafka_global_recomendations();
    }

    public init_kafka_global_recomendations() {
        const readTopicName = 'requestMusicList';
        const writeTopicName = 'musicList';
        const kafkaHost = process.env.KAFKA_URL || KAFKA_URL;

        const client = new KafkaClient({
            kafkaHost: kafkaHost,
        });
        const producer = new HighLevelProducer(client);

        producer.on('ready', () => {
            const topicsToCreate = [
                {
                    topic: readTopicName,
                    partitions: 1,
                    replicationFactor: 1,
                },
                {
                    topic: writeTopicName,
                    partitions: 1,
                    replicationFactor: 1,
                },
            ];
            client.createTopics(topicsToCreate, (err, data) => {
                const consumer = new Consumer(
                    client,
                    [{ topic: readTopicName, partition: 0 }],
                    {}
                );

                consumer.on('message', async (message) => {
                    const musicList: MongooseDocument = await this.audioModel.find();
                    const stringMessage = JSON.stringify(musicList);
                    const payloads = [
                        { topic: writeTopicName, messages: stringMessage },
                    ];
                    producer.send(payloads, (err, data) => {});
                });
            });
        });
    }
}
