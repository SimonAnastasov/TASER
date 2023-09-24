import { rest } from 'msw'

const baseUrl = 'http://localhost:8080/api'

export const handlers = [
    rest.post('http://localhost:8080/api/login', (req, res, ctx) => {
        const { username, password } = req.body
        console.log('username: ', username)
        if (username == 'testuser' && password == 'testuser') {
            return res(
                ctx.status(200),
                ctx.json({
                    bearerToken: 'mocked_user_token',
                }),
            )
        } else {
            return res(
                ctx.status(400),
                ctx.json({
                    "error": true,
                    "message": "You entered invalid credentials."
                }),
            )
        }
    }),



]