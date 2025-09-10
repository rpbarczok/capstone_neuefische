import Title from "../components/Title.tsx";

export default function HomePage () {
    return (
        <>
            <Title title="Die Terrarium-Tracker-App" cssClass="start"/>
            <div>
                <p>
                    Dies ist mein Abschlussprojekt für das <a href="https://www.neuefische.de/bootcamp/java-development">
                    Java Development Bootcamp </a> von <a href="https://www.neuefische.de">Neue Fische</a>. Ich programmiere
                    hier mit <a href="https://spring.io/projects/spring-boot">Java Spring Boot</a> im Backend und <a href="https://react.dev/">
                    React</a> (natürlich mit Typescript!) im Frontend eine Applikation, mit der man seine Terrarientiere
                    besser organisieren kann.
                </p>
                <p>
                    Die Tiere haben unterschiedliche Rhythmen und Bedürfnisse: Die einen müssen alle zwei Tage, die anderen nur alle
                    zwei Wochen gefüttert werden, die einen brauchen 60 % Luftfeuchtigkeit und die andern 90 % usw. Da kann man leicht
                    durcheinander kommen. Da soll diese App Abhilfe schaffen.
                </p>
            </div>
        </>

    )
}